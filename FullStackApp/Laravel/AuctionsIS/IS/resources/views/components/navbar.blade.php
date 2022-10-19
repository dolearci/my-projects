<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><div class="logo">Logo</div></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
  
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Vyberte si kategorie <span class="sr-only">(current)</span>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">    
                    <a class="dropdown-item" href="/">Všechny kategorie</a>
                    @foreach ($category_list as $category)
                    <a class="dropdown-item" href="{{$category->path()}}">{{ $category->title }}</a>
                    @endforeach
                </div>
            </li>
            <!-- <li class="nav-item">
            <a class="nav-link" href="#hot">Žhavé aukce</a>
            </li> -->
            <li class="nav-item">
            <a class="nav-link" href="/auctions/ending">Končící aukce</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="/auctions/new">Nové aukce</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="/auctions/upcoming">Budoucí aukce</a>
            </li>
            @auth
            <li class="nav-item">
            <a class="nav-link" href="/auctions/results">Výsledky</a>
            </li>
            @endauth
        </ul>
        <form class="form-inline" role="search" method="POST" action="/search">
            @csrf
            <div class="form-group">
                <input type="text" class="form-control mr-sm-2" placeholder="Hledat.." name="text">                        
                <x-category-dropdown/>
            </div>
            <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Hledat</button>
        </form>
        <ul class="navbar-nav mr-left">
            @guest
            <li class="nav-item"><a class="nav-link" href="/login">Přihlásit se</a></li>
            @else
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    {{ Auth::user()->name }}
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">    
                    <a class="dropdown-item" href="/auctions/my-auctions">Moje aukce</a>
                    @can('admin_or_auctioneer')
                    <a class="dropdown-item" href="/auctions/unapproved">Neschválené aukce</a>
                    <a class="dropdown-item" href="/auctions/approved">Schválené aukce</a>
                    @endcan
                    @can('admin')
                    <a class="dropdown-item" href="/users">Spravovat uživatele</a>
                    @endcan
                    <a class="dropdown-item" href="{{ route('logout') }}"
                        onclick="event.preventDefault();
                                    document.getElementById('logout-form').submit();">
                        {{ __('Odhlásit se') }}
                    </a>
                    <form id="logout-form" action="{{ route('logout') }}" method="POST" class="d-none">
                        @csrf
                    </form>
                </div>
            </li>
            @endguest
            <li class="nav-item"><a class="nav-link" href="/auctions/create">Prodat</a></li>
        </ul>
    </div>
</nav>