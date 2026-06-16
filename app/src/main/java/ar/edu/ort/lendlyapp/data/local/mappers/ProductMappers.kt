package ar.edu.ort.lendlyapp.data.local.mappers

import ar.edu.ort.lendlyapp.data.local.entities.ProductEntity
import ar.edu.ort.lendlyapp.data.remote.dto.ProductDto

fun ProductDto.toEntity() = ProductEntity(
    id = id,
    name = name,
    brand = brand,
    category = category,
    price = price,
    currency = currency,
    image = image,
    monthlyInstallment = monthlyInstallment,
    installmentMonths = installmentMonths,
    interestRate = interestRate,
    isFeatured = isFeatured,
    isAvailable = isAvailable,
    rating = rating,
    reviewCount = reviewCount,
    description = description
)

fun ProductEntity.toDto() = ProductDto(
    id = id,
    name = name,
    brand = brand,
    category = category,
    price = price,
    currency = currency,
    image = image,
    monthlyInstallment = monthlyInstallment,
    installmentMonths = installmentMonths,
    interestRate = interestRate,
    isFeatured = isFeatured,
    isAvailable = isAvailable,
    rating = rating,
    reviewCount = reviewCount,
    description = description
)
