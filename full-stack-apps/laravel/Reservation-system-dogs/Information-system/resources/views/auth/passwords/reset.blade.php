@extends('layouts.app')

@section('content')

<form class="form-signin" method="POST" action="{{ route('password.update') }}">
    @csrf

    <input type="hidden" name="token" value="{{ $token }}">
    <h1 class="h3 mb-3 font-weight-normal">Obnovit heslo</h1>

    <label for="email" class="sr-only">E-mail</label>

    <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email"
        value="{{ $email ?? old('email') }}" required autocomplete="email" autofocus placeholder="Email">

    @error('email')
    <span class="invalid-feedback" role="alert">
        <strong>{{ $message }}</strong>
    </span>
    @enderror

    <label for="password" class="sr-only">Nové heslo</label>

    <input id="password" type="password" class="form-control @error('password') is-invalid @enderror" name="password"
        required autocomplete="new-password" placeholder="Nové heslo">

    @error('password')
    <span class="invalid-feedback" role="alert">
        <strong>{{ $message }}</strong>
    </span>
    @enderror

    <label for="password-confirm" class="sr-only">Potvrdit heslo</label>

    <input id="password-confirm" type="password" class="form-control" name="password_confirmation" required
        autocomplete="new-password" placeholder="Potvrdit heslo">

    <button type="submit" class="btn btn-blue btn-lg btn-block">
        Obnovit heslo
    </button>
</form>
@endsection
