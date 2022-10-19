@extends('layouts.app')

@section('content')
    <form class="form-signin" method="POST" action="{{ route('login') }}">
        @csrf
        
        <h1 class="mb-5">Zvierací útulok <span class="badge badge-custom">Hafkáči</span></h1>
        
        <h2 class="h3 mb-3 font-weight-normal">Prihlásenie pre zamestnancov</h2>

        <label for="email" class="sr-only">Email</label>
        <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email"
            value="{{ old('email') }}" required autocomplete="email" autofocus placeholder="Email">

        @error('email')
        <span class="invalid-feedback" role="alert">
            <strong>{{ $message }}</strong>
        </span>
        @enderror

        <label for="password" class="sr-only">Heslo</label>
        <input id="password" type="password" class="form-control @error('password') is-invalid @enderror"
            name="password" required autocomplete="current-password" placeholder="Heslo">

        @error('password')
        <span class="invalid-feedback" role="alert">
            <strong>{{ $message }}</strong>
        </span>
        @enderror

        <div class="checkbox mb-3">
            <label class="form-check-label" for="remember">
                <input class="form-check-input" type="checkbox" value="remember-me" name="remember" id="remember"
                    {{ old('remember') ? 'checked' : '' }}> Pamätať si ma
            </label>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-lg btn-blue btn-block">
                Prihlásiť sa
            </button>

            @if (Route::has('password.request'))
            <a class="btn btn-link" href="{{ route('password.request') }}">
                Zabudnuté heslo?
            </a>
            @endif
        </div>
    </form>
@endsection
