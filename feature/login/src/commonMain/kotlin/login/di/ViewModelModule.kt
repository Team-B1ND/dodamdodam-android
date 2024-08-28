package login.di

import login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel { LoginViewModel() }
}
