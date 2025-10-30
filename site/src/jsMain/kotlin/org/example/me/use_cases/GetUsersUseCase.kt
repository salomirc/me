package org.example.me.use_cases


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.me.repositories.ResponseState.ActiveResponseState
import org.example.me.repositories.activeResponseStateWrapper
import org.example.me.models.domain.UserModel
import org.example.me.repositories.IBlogRepository

interface IGetUsersUseCase {
    suspend fun getUsers(): Flow<ActiveResponseState<List<UserModel>>>
}

class GetUsersUseCase(
    private val repository: IBlogRepository
): IGetUsersUseCase {

    override suspend fun getUsers(): Flow<ActiveResponseState<List<UserModel>>> {
        return withContext(Dispatchers.Default) {
            repository
                .getUsers()
                .map { responseState ->
                    responseState.activeResponseStateWrapper { users ->
                        users.map { user ->
                            user.copy(
                                name = user.name.uppercase()
                            )
                        }
                    }
                }
        }
    }
}