package ru.testapp.cft_test.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.testapp.cft_test.dto.User

@Entity
data class EntityUser(
    @PrimaryKey(autoGenerate = true)
    val initialId: Int? = null,
    val gender: String? = null,
    @Embedded
    val name: UserFullNameEmbeddable? = null,
    val location: LocationEmbeddable,
    val email: String? = null,
    @Embedded
    val login: LoginEmbeddable? = null,
    @Embedded
    val dob: DobEmbeddable? = null,
    @Embedded
    val registered: RegisteredEmbeddable? = null,
    val phone: String? = null,
    val cell: String? = null,
    @Embedded
    val userId: IdEmbeddable? = null,
    @Embedded
    val picture: PictureEmbeddable? = null,
    val nat: String? = null
) {

    fun toDto(): User =
        User(
            initialId,
            gender,
            name,
            location,
            email,
            login,
            dob,
            registered,
            phone,
            cell,
            userId,
            picture,
            nat
        )

    companion object {
        fun toEntity(dto: User): EntityUser = EntityUser(
            dto.initialId,
            dto.gender,
            dto.name,
            dto.location,
            dto.email,
            dto.login,
            dto.dob,
            dto.registered,
            dto.phone,
            dto.cell,
            dto.userId,
            dto.picture,
            dto.nat
        )
    }
}

@Entity
data class LocationEmbeddable(
    @PrimaryKey(autoGenerate = true)
    val locationId: Int?,
    @Embedded
    val street: StreetEmbeddable? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val postcode: Long? = null,
    @Embedded
    val coordinates: CoordinatesEmbeddable? = null,
    @Embedded
    val timeZone: TimeZoneEmbeddable? = null
)

data class StreetEmbeddable(
    val number: Long? = null,
    val name: String? = null
)

data class CoordinatesEmbeddable(
    val latitude: String? = null,
    val longitude: String? = null
)


data class TimeZoneEmbeddable(
    val offset: String? = null,
    val description: String? = null
)


data class UserFullNameEmbeddable(
    val title: String? = null,
    val first: String? = null,
    val last: String? = null
)


data class LoginEmbeddable(
    val uuid: String? = null,
    val username: String? = null,
    val password: String? = null,
    val salt: String? = null,
    val md5: String? = null,
    val sha1: String? = null,
    val sha256: String? = null
)


data class DobEmbeddable(
    val dobDate: String? = null,
    val dobAge: Int? = null
)


data class RegisteredEmbeddable(
    val registeredDate: String? = null,
    val registeredAge: Int? = null
)

data class IdEmbeddable(
    val idName: String? = null,
    val value: String? = null
)

data class PictureEmbeddable(
    val large: String? = null,
    val medium: String? = null,
    val thumbnail: String? = null
)


