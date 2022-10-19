package config

import (
	"fmt"
	"github.com/dolearci/go-microservice/model"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"os"
)

type DatabaseConfig struct {
	Host     string
	Port     int
	User     string
	Password string
	DBName   string
}

func getEnv(key, fallback string) string {
	if value, exists := os.LookupEnv(key); exists {
		return value
	}
	return fallback
}

func GetDatabaseConfig() DatabaseConfig {
	return DatabaseConfig{
		Host:     getEnv("DB_HOST", "localhost"),
		Port:     5432,
		User:     "user",
		Password: "password",
		DBName:   "user_microservice",
	}
}

func ConnectToDatabase() *gorm.DB {
	dbConfig := GetDatabaseConfig()
	dbConnection := fmt.Sprintf("postgresql://%s:%s@%s:%d/%s",
		dbConfig.User, dbConfig.Password, dbConfig.Host, dbConfig.Port, dbConfig.DBName)
	fmt.Println(dbConnection)
	db, err := gorm.Open(postgres.Open(dbConnection), &gorm.Config{})
	if err != nil {
		panic("Connection to database failed")
	}

	_ = db.Exec("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\"")

	if err := db.AutoMigrate(&model.User{}); err != nil {
		panic("Migration of User table failed")
	}

	return db
}
