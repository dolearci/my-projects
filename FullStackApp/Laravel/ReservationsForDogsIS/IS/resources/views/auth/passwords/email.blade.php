@extends('layouts.app')

@section('content')

<form class="form-signin" method="POST" action="{{ route('password.email') }}">
    @csrf
    <h1 class="h3 mb-3 font-weight-normal">Obnovit heslo</h1>
    @if (session('status'))
    <div class="row">
        <div class="alert alert-success" role="alert">
            {{ session('status') }}
        </div>
    </div>
    @else
    <div class="mb-3">
        <label for="email" class="sr-only">E-mail</label>

        <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email"
            value="{{ old('email') }}" required autocomplete="email" autofocus placeholder="Email">

        @error('email')
        <span class="invalid-feedback" role="alert">
            <strong>{{ $message }}</strong>
        </span>
        @enderror
    </div>
    <button type="submit" class="btn btn-blue btn-block btn-lg">
        Odeslat odkaz pro obnovu hesla
    </button>
    <a class="btn btn-link" href="{{ route('login') }}">
        Zpět
    </a>
    @endif

</form>
@endsection
