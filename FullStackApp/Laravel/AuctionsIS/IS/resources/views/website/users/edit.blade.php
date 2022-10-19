@extends('layouts.website')

@section('content')
<h1>Upravit uživatele</h1>
    <form action="/users/{{ $user->id }}" method="POST">
        @csrf
        {{ method_field('PUT') }}
        <div class="form-group">

                <label for="name">Jméno</label>
                <input id="name" type="text" class="form-control @error('name') is-invalid @enderror"
                    name="name" value="@if($errors->any()){{ old('name') }}@else{{ $user->name }}@endif" required autofocus>

                @error('name')
                <div class="error">{{ $message }}</div>
                @enderror
        </div>
        <div class="form-group">
                <label for="email">Email</label>
                <input id="email" type="email" class="form-control @error('email') is-invalid @enderror"
                    name="email" value="@if($errors->any()){{ old('email') }}@else{{ $user->email }}@endif" required autocomplete="email" autofocus>

                @error('email')
                <div class="error">{{ $message }}</div>
                @enderror
        </div>
        <div class="form-group">
            <label for="role">Role</label>
            <select class="form-control" id="role" name="role">
                <option @if($user->role == "admin") selected @endif value="admin">Admin</option>
                <option @if($user->role == "auctioneer") selected @endif value="auctioneer">Auctioneer</option>
                <option @if($user->role == "user") selected @endif value="user">User</option>
            </select>
            @error('role')
                <div class="error">{{ $message }}</div>
            @enderror
        </div>

        <button type="submit" class="btn btn-primary">Uložit</button>
    </form>
@endsection