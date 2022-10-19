FROM golang:1.22 as builder

WORKDIR /app
COPY . .

RUN go build -v -o user-microservice

FROM golang:1.22
COPY --from=builder /app/user-microservice /user-microservice

CMD ["/user-microservice"]
