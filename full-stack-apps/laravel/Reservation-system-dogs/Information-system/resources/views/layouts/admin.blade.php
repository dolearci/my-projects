<!doctype html>
<html lang="sk">

<!--
|---------------------------------------
|   Autor: Daniel Havranek (xhavra18)
|---------------------------------------
|
-->

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">

    <title>{{ config('app.name') }} | Admin</title>
    <meta name="robots" content="noindex, nofollow">

    <!-- Styles -->

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">

    <link href="{{ asset('css/simple-sidebar.css') }}" rel="stylesheet">
    <link href="{{ asset('css/admin.css') }}" rel="stylesheet">
    
    <!-- Fonts -->

    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600;700&display=swap" rel="stylesheet">

</head>

<body>
    <div class="d-flex" id="wrapper">

        <div class="bg-blue border-right" id="sidebar-wrapper">
            <div class="sidebar-heading">Útulok Hafkáči</div>
            <div class="list-group list-group-flush">
                <a href="/admin" class="list-group-item list-group-item-action bg-blue"><i class='fas fa-chart-bar fa-fw'></i>
                    Nástenka</a>
                <a href="/admin/walks" class="list-group-item list-group-item-action bg-blue"><i class="fas fa-paw fa-fw"></i> Venčenie</a>
                <a href="/admin/dogs" class="list-group-item list-group-item-action bg-blue"><i class="fas fa-dog fa-fw"></i> Psíkovia</a>
                <a href="/admin/users" class="list-group-item list-group-item-action bg-blue"><i
                        class='fas fa-user-friends fa-fw'></i> Zamestnanci</a>
                <a href="/admin/profile" class="list-group-item list-group-item-action bg-blue"><i
                        class='fas fa-user fa-fw'></i> Profil</a>
            </div>
        </div>

        <div id="page-content-wrapper">

            <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
                <button class="btn btn-light" id="menu-toggle"><i class="fas fa-bars"></i></button>

                <button class="navbar-toggler btn btn-light" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <i class="fas fa-bars"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="{{ route('home') }}" target="_blank"><i class='fas fa-eye'></i> Zobrazit web</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a id="navbarDropdown" class="nav-link dropdown-toggle" href="#" role="button"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" v-pre>
                                {{ Auth::user()->name }} <span class="caret"></span>
                            </a>
                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="/admin/profile"><i class='fas fa-user'></i> Profil</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="{{ route('logout') }}" onclick="event.preventDefault();
                                                   document.getElementById('logout-form').submit();">
                                    <i class="fas fa-sign-out-alt"></i> Odhlásiť sa
                                </a>

                                <form id="logout-form" action="{{ route('logout') }}" method="POST"
                                    style="display: none;">
                                    @csrf
                                </form>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>

            <div class="container-fluid">
                <main>
                    @yield('content')
                </main>
            </div>
        </div>

    </div>

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous">
    </script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
    </script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    <script src="{{ asset('js/main.js') }}"></script>
    
    @yield('scripts')

</body>

</html>
