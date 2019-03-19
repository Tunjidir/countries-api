package com.oniyide.business.boundary

import com.oniyide.business.control.service.CountryService
import com.oniyide.business.entity.AddCountryRequest
import com.oniyide.business.entity.Country
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/countries")
class CountriesController(
  private val countryService: CountryService
) {
  
  @PostMapping
  fun addCountry(@RequestBody countryRequest: AddCountryRequest) : ResponseEntity<Country> {
    return ResponseEntity.ok(countryService.addCountry(countryRequest))
  }
  
  @GetMapping
  fun getCountries() : ResponseEntity<List<Country>> {
    return ResponseEntity.ok(countryService.getCountries())
  }
  
  @PutMapping("{/id}")
  fun updateCountry( @PathVariable(name = "id") id: Long,
                     @RequestBody countryRequest: AddCountryRequest)
          : ResponseEntity<Country> {
    return ResponseEntity.ok(countryService.updateCountry(id, countryRequest))
  }
  
  @DeleteMapping("{/id}")
  fun deleteCountry(@PathVariable(name = "id") id: Long) : ResponseEntity<Any> {
    countryService.deleteCountry(id)
    return ResponseEntity.ok().build()
  }
}