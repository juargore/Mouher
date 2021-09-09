package com.glass.domain.usecases.user

import com.glass.domain.entities.*
import com.glass.domain.repositories.IUserRepository
import io.reactivex.Single

class UserUseCase(
    private val userRepository: IUserRepository
): IUserUseCase {

    override fun signIn(email: String, password: String): Single<LoginData> {
        return userRepository.signIn(email, password)
    }


    override fun signUp(
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String ): Single<RegistrationData> {

        return userRepository.addOrUpdateUser(
            false,
            null,
            "",
            name,
            fLastName,
            mLastName,
            gender,
            birthday,
            phone,
            email,
            password
        )
    }


    override fun updateUser(
        id: Int,
        code: String,
        name: String,
        fLastName: String,
        mLastName: String,
        gender: Int,
        birthday: String,
        phone: String,
        email: String,
        password: String
    ): Single<RegistrationData> {

        return userRepository.addOrUpdateUser(
            true,
            id,
            code,
            name,
            fLastName,
            mLastName,
            gender,
            birthday,
            phone,
            email,
            password
        )
    }


    override fun getUserData(userId: Int): Single<UserProfileData> {
        return userRepository.getUserData(userId)
    }


    override fun recoverPassword(email: String): Single<ResponseUI> {
        return userRepository.recoverPassword(email).map { it.toResponseUI() }
    }


    override fun getCountriesAsList(): Single<List<Country>> {
        return userRepository.getCountriesOrStates(
            getCountries = true,
            countryId = null
        ).map { data->
            val countries = data.Paises?.filter { it.IdPais == 117 }?.toMutableList()
            countries?.add(0, Country(0, "País *"))
            return@map countries
        }
    }


    override fun getStatesAsList(countryId: Int): Single<List<State>> {
        return userRepository.getCountriesOrStates(
            getCountries = false,
            countryId = countryId
        ).map { data->
            val states = data.Estados?.toMutableList()
            states?.add(0, State(0, "Estado *"))
            return@map states
        }
    }


    override fun addOrUpdateAddress(
        isUpdatingAddress: Boolean,
        id: Int?,
        userId: Int,
        addressType: Int,
        street: String,
        intNumber: String,
        extNumber: String,
        crosses: String,
        suburb: String,
        postalCode: String,
        countryId: Int,
        stateId: Int,
        municipality: String
    ): Single<RegistrationData> {

        return userRepository.addOrUpdateAddress(
            isUpdatingAddress = isUpdatingAddress,
            id = id,
            userId = userId,
            addressType = 1,
            street = street,
            intNumber = intNumber,
            extNumber = extNumber,
            crosses = crosses,
            suburb = suburb,
            postalCode = postalCode,
            countryId = countryId,
            stateId = stateId,
            municipality = municipality
        )
    }

}