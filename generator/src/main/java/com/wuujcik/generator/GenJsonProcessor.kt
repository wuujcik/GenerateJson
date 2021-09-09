package com.wuujcik.generator

import javax.annotation.processing.Processor
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asTypeName
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class GenJsonProcessor : AbstractProcessor() {

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        roundEnv?.getElementsAnnotatedWith(GenJson::class.java)?.let { element ->
            element.forEach {
                val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                generateClass(it, pack)
            }
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(GenJson::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    private fun generateClass(elementClass: Element, pack: String) {
        val fileName = "${elementClass.simpleName}Generated"
        val tripleQuote = "\"\"\""
        val body = mutableListOf<String>()
        elementClass.enclosedElements.forEach { element ->
            when(element.kind) {
                ElementKind.FIELD -> {
                    body.add(""""$element": "${'$'}$element"""")
                }
                else -> { }
            }
        }

        val bodyString = body.joinToString(", ")
        val statement = """return $tripleQuote{$bodyString}$tripleQuote"""
        val file = FileSpec.builder(pack, fileName)
            .addFunction(
                FunSpec.builder("toJson")
                    .returns(String::class)
                    .receiver(elementClass.asType().asTypeName())
                    .addStatement(statement)
                    .build())
            .build()

        val generatedDir = processingEnv.options[GENERATED_NAME]
        file.writeTo(File(generatedDir, "$fileName.kt"))
    }

    companion object {
        const val GENERATED_NAME = "kapt.kotlin.generated"
    }
}