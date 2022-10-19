package model

import (
	"github.com/google/uuid"
	"time"
)

// User represents a user model.
type User struct {
	ID          uint `gorm:"primaryKey"`
	Name        string
	Email       string `gorm:"uniqueIndex"`
	DateOfBirth time.Time
	ExternalId  uuid.UUID `gorm:"type:uuid;default:uuid_generate_v4();uniqueIndex" json:"id"`
}
