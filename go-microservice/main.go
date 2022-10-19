package main

import (
	"github.com/dolearci/go-microservice/config"
)

func main() {
	router := config.SetupRouter()

	if err := router.Run(":8080"); err != nil {
		panic("Failed to start the server")
	}
}
