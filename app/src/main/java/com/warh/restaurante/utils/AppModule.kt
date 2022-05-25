package com.warh.restaurante.utils

import com.warh.restaurante.dao.ProductDao
import com.warh.restaurante.dao.ProductDaoImpl
import com.warh.restaurante.dao.UserDao
import com.warh.restaurante.dao.UserDaoImpl
import com.warh.restaurante.repository.ProductRepository
import com.warh.restaurante.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun providesUserDao() : UserDao = UserDaoImpl()

    @Singleton
    @Provides
    fun providesUserRepository(userDao: UserDao) : UserRepository = UserRepository(userDao)

    @Singleton
    @Provides
    fun providesProductDao() : ProductDao = ProductDaoImpl()

    @Singleton
    @Provides
    fun providesProductRepository(productDao: ProductDao) : ProductRepository = ProductRepository(productDao)
}