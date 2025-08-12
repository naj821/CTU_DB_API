package com.kapston.CTU_DB_API.utility

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import com.kapston.CTU_DB_API.domain.Enums.Role

@Converter(autoApply = true)
class RoleEnumConverter : AttributeConverter<Role, String> {
    override fun convertToDatabaseColumn(attribute: Role?): String? =
        attribute?.name

    override fun convertToEntityAttribute(dbData: String?): Role? =
        dbData?.let { Role.valueOf(it) }
}
