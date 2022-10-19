<!--
|---------------------------------------
|   Autor: Daniel Olearčin (xolear00)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
<h1>Upraviť zamestnanca</h1>
    <form action="/admin/users/{{ $user->id }}" method="POST">
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
        <a href="/admin/users" class="btn btn-white">Zpět</a>
    </form>
@endsection