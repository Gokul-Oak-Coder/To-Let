package com.example.to_let.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.to_let.model.Properties
import androidx.compose.runtime.State

class PropertyViewModel : ViewModel() {
    private val _properties = mutableStateOf<List<Properties>>(emptyList())
    val properties: State<List<Properties>> get()= _properties

    fun addProperty(property: Properties) {
        _properties.value += property
    }

    fun getPropertyById(id: String): Properties? {
        return _properties.value.find { it.id == id }
    }

    fun updateProperty(updatedProperty: Properties) {
        _properties.value = _properties.value.map {
            if (it.id == updatedProperty.id) updatedProperty else it
        }
    }

    fun deleteProperty(property: Properties) {
        _properties.value = _properties.value.filterNot { it.id == property.id }
    }
}