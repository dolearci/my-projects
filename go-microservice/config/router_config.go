package config

import (
	"github.com/dolearci/go-microservice/handler"
	"github.com/dolearci/go-microservice/repository"
	"github.com/gin-gonic/gin"
)

func SetupRouter() *gin.Engine {
	database := ConnectToDatabase()
	router := gin.Default()

	userRepository := repository.NewUserRepository(database)
	userHandler := handlers.NewUserHandler(userRepository)

	router.GET("/", userHandler.GetAllUsers)
	router.GET("/:id", userHandler.GetUserByID)
	router.DELETE("/:id", userHandler.DeleteUserByID)
	router.DELETE("/", userHandler.DeleteAllUsers)
	router.POST("/save", userHandler.CreateUser)
	router.PUT("/:id", userHandler.UpdateUser)
	return router
}
