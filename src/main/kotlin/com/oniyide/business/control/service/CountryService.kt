package com.oniyide.business.control.service

import com.oniyide.business.control.ResourceNotFoundException
import com.oniyide.business.control.repository.CountryRepository
import com.oniyide.business.entity.AddCountryRequest
import com.oniyide.business.entity.Country
import com.oniyide.business.entity.toCountry
import org.springframework.stereotype.Service

interface CountryService {

    fun addCountry(countryRequest: AddCountryRequest): Country

    fun deleteCountry(id: Long)

    fun updateCountry(id: Long, countryRequest: AddCountryRequest): Country

    fun getCountries(): List<Country>

}

@Service
class CountryServiceImpl(
    private val countryRepository: CountryRepository
) : CountryService {

    override fun addCountry(countryRequest: AddCountryRequest): Country {
        val country = countryRequest.toCountry()
        return countryRepository.save(country)
    }

    override fun deleteCountry(id: Long) {
        countryRepository.deleteById(id)
    }

    override fun updateCountry(id: Long, countryRequest: AddCountryRequest): Country {
        val country = countryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("country with $id not found") }

        return country.apply {
            name = countryRequest.name
            continent = countryRequest.continent
        }
    }

    override fun getCountries(): List<Country> {
        return countryRepository.getCountries()
    }
}