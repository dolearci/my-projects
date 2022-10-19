# IIS

php: ^7.2.5
laravel/framework: ^7.29

1. Run git clone https://github.com/nesty156/IIS.git

2. Move to laravel root folder

3. Create these folders under storage/framework:
 - sessions
 - views
 - cache

4. Run composer install
5. Run cp .env.example .env
6. Run php artisan key:generate
7. Run php artisan migrate
8. Run php artisan serve

9. Go to link localhost:8000

