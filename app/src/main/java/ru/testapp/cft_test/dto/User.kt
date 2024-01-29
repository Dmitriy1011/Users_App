package ru.testapp.cft_test.dto

import com.google.gson.annotations.SerializedName
import ru.testapp.cft_test.entity.DobEmbeddable
import ru.testapp.cft_test.entity.IdEmbeddable
import ru.testapp.cft_test.entity.LocationEmbeddable
import ru.testapp.cft_test.entity.LoginEmbeddable
import ru.testapp.cft_test.entity.PictureEmbeddable
import ru.testapp.cft_test.entity.RegisteredEmbeddable
import ru.testapp.cft_test.entity.UserFullNameEmbeddable

data class Results(
    val results: List<User>? = null
)

data class User(
    val initialId: Int? = null,
    val gender: String? = null,
    val name: UserFullNameEmbeddable? = null,
    val location: LocationEmbeddable,
    val email: String? = null,
    val login: LoginEmbeddable? = null,
    val dob: DobEmbeddable? = null,
    val registered: RegisteredEmbeddable? = null,
    val phone: String? = null,
    val cell: String? = null,
    @SerializedName("id")
    val userId: IdEmbeddable? = null,
    val picture: PictureEmbeddable? = null,
    val nat: String? = null
)


