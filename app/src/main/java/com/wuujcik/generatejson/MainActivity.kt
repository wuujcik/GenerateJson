package com.wuujcik.generatejson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.wuujcik.generatejson.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        val user = createUser()
        val contact = createContactDetails()
        binding.userData.text = user.toJson()
        binding.contact.text = contact.toJson()
    }

    private fun createUser(): User {
        return User(
            firstName = "Aleksandra",
            lastName = "Wójcikowska",
            position = "Junior Android Developer",
            company = "Applifting"
        )
    }

    private fun createContactDetails(): ContactDetails {
        return ContactDetails(
            emailAddress = "aleksandra.wojcikowska@tutanota.com",
            phoneNumber = "735007975"
        )
    }
}
