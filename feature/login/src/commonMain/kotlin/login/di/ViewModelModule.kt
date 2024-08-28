package login.di

import com.b1nd.dodam.data.login.repository.LoginRepository
import login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel { LoginViewModel() }
}
