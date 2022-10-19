// integration_test.go
package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/dolearci/go-microservice/config"
	"github.com/dolearci/go-microservice/model"
	"github.com/google/uuid"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"
)

func TestCreateAndGetUser(t *testing.T) {
	router := config.SetupRouter()
	server := httptest.NewServer(router)
	defer server.Close()

	testEmail := "test" + fmt.Sprintf("%d", time.Now().Unix()) + "@example.com"

	user := model.User{
		Name:        "Test User",
		Email:       testEmail,
		DateOfBirth: time.Now(),
		ExternalId:  uuid.New(),
	}

	userJSON, _ := json.Marshal(user)
	resp, err := http.Post(fmt.Sprintf("%s/save", server.URL), "application/json", bytes.NewBuffer(userJSON))
	if err != nil || resp.StatusCode != http.StatusOK {
		t.Fatalf("Failed to create user: %v", err)
	}

	var createdUser model.User
	if err := json.NewDecoder(resp.Body).Decode(&createdUser); err != nil {
		t.Fatalf("Failed to decode response: %v", err)
	}

	req, _ := http.NewRequest("GET", fmt.Sprintf("%s/%d", server.URL, createdUser.ID), nil)
	resp, err = http.DefaultClient.Do(req)
	if err != nil || resp.StatusCode != http.StatusOK {
		t.Fatalf("Failed to get user: %v", err)
	}

	var fetchedUser model.User
	if err := json.NewDecoder(resp.Body).Decode(&fetchedUser); err != nil || fetchedUser.ID != createdUser.ID {
		t.Fatalf("User fetched does not match expected ID")
	}
}
