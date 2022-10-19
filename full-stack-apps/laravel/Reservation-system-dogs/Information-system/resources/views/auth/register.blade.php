@extends('layouts.admin')

@section('content')
<h1>Pridať zamestnanca</h1>
<form method="POST" action="{{ route('register') }}">
    @csrf

    <div class="form-group row">

        <div class="col-md-6">
            <label for="name">Meno</label>
            <input id="name" type="text" class="form-control @error('name') is-invalid @enderror" name="name"
                value="{{ old('name') }}" required autocomplete="name" autofocus>

            @error('name')
            <div class="error">{{ $message }}</div>
            @enderror
        </div>
    </div>

    <div class="form-group row">

        <div class="col-md-6">
            <label for="email">E-mail</label>
            <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email"
                value="{{ old('email') }}" required autocomplete="email">

            @error('email')
            <div class="error">{{ $message }}</div>
            @enderror
        </div>
    </div>

    <div class="form-group row">

        <div class="col-md-6">
            <label for="password">Heslo</label>
            <input id="password" type="password" class="form-control @error('password') is-invalid @enderror"
                name="password" required autocomplete="new-password">

            @error('password')
            <div class="error">{{ $message }}</div>
            @enderror
        </div>
    </div>

    <div class="form-group row">
        <div class="col-md-6">
            <label for="password-confirm">Potvrdiť heslo</label>
            <input id="password-confirm" type="password" class="form-control" name="password_confirmation" required
                autocomplete="new-password">
        </div>
    </div>

            <button type="submit" class="btn btn-blue">
                Uložiť
            </button>
            <a href="/admin/users" class="btn btn-white">Späť</a>
</form>
@endsection
