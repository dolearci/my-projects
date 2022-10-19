<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
    <h1>Profil</h1>
    @if(Session::has('custom_message'))
        <div class="alert alert-success" role="alert">
            {{ Session::get('custom_message') }}
        </div>
    @endif
    <form action="/admin/profile/{{ $user->id }}" method="POST">
        @csrf
        {{ method_field('PUT') }}
        <div class="form-group row">
            <div class="col-md-6">
                <label for="name">Jméno</label>
                <input id="name" type="text" class="form-control @error('name') is-invalid @enderror"
                    name="name" value="@if($errors->any()){{ old('name') }}@else{{ $user->name }}@endif" required autofocus>
                @error('name')
                    <div class="error">{{ $message }}</div>
                @enderror
            </div>
        </div>
        <div class="form-group row">

            <div class="col-md-6">
                <label for="email">Email</label>
                <input id="email" type="email" class="form-control @error('email') is-invalid @enderror"
                    name="email" value="@if($errors->any()){{ old('email') }}@else{{ $user->email }}@endif" required autocomplete="email" autofocus>

                @error('email')
                <div class="error">{{ $message }}</div>
                @enderror
            </div>
        </div>
        <button type="submit" class="btn btn-blue">Uložit</button>
    </form>
@endsection
