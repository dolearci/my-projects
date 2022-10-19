<!DOCTYPE html>
<html lang="sk">

<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
|---------------------------------------
|
-->

<head>
    <meta charset="UTF-8">
    <title>{{ config('app.name') }}</title>
    <meta name="description" content="">
    <meta name=”robots” content="index, follow">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}" />

    <!-- Styles -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Fuzzy+Bubbles:wght@700&family=Source+Sans+Pro:ital,wght@0,400;0,600;0,700;1,400;1,600;1,700&display=swap" rel="stylesheet"> 
    <link rel="stylesheet" href="{{ asset('css/main.css') }}">

    <!-- Fonts -->

</head>

<body>
    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="{{ route('home') }}">
                <!--{{ config('app.name') }}-->
                <span class="badge badge-custom">Hafkáči</span>
            </a>
            <button class="navbar-toggler custom-toggler" type="button" data-toggle="collapse" data-target="#navbarLinks"
                aria-controls="navbarLinks" aria-expanded="false" aria-label="Toggle navigation">
                Menu
            </button>
            <div class="collapse navbar-collapse" id="navbarLinks">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item @if(Request::path() === '/') active @endif">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link @if(Request::path() === '/info') active @endif" href="/info">Informácie</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        

        @yield('content')

    </div>
    <!-- <footer>
    <a class="nav-link" href="/login">Prihlasenie pre zamestnancov</a>
    </footer> -->
    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
    </script>

    <script>
        $(document).ready(function(){
            day_selected = false;
            time_selected = false;

            $.ajaxSetup({
                headers: {
                    'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
                }
            });

            $('#day h2').animate({'opacity': 1}, 800);
            $('#day .btn-group').delay(800).animate({'opacity': 1}, 800);

            $('input[name="day"]').click(function() {
                if (!day_selected) {
                    $('#datetime').css('margin-top', '20vh');
                }
                day_selected = true;
                $('#time h2').animate({'opacity': 1}, 500); 
                $('#time .btn-group').delay(200).animate({'opacity': 1}, 500);
            });
            $('input[name="time"]').click(function() {
                $('#datetime').css('margin-top', '0vh');
                time_selected = true;
                $('#cards-heading').animate({'opacity': 1}, 500); 
            });

            $('input[name="time"], input[name="day"]').click(function(e){
                $('#cards').animate({'opacity': 0}, 50); 
                if (time_selected && day_selected) {
                    $.ajax({
                        url: "{{ url('/unreserved') }}",
                        method: 'GET',
                        data: {
                            day: $('input[name="day"]:checked').val(),
                            time: $('input[name="time"]:checked').val()
                        },
                        dataType: 'json',
                        success: function(data){
                            $('#cards').html(data.records).animate({'opacity': 1}, 300); 
                            //console.log(data.records);
                    }});
                }

            });

               
        });
    </script>

</body>

</html>
