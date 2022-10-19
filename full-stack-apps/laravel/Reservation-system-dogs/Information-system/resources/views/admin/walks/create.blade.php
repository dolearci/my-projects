<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
<h1>Přidat venčenie</h1>
<form action="/admin/walks" method="POST">
    @csrf
    <div class="form-group">
        <label for="name">Meno</label>
        <input type="text" name="name" class="form-control @error('name') is-invalid @enderror" value="{{ old('name') }}">
        @error('name')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="email">Email</label>
        <input type="email" name="email" class="form-control @error('email') is-invalid @enderror" value="{{ old('email') }}">
        @error('email')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="address">Adresa bydliště</label>
        <input type="text" name="address" class="form-control @error('address') is-invalid @enderror" value="{{ old('address') }}">
        @error('address')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="phone">Telefon</label>
        <input type="tel" name="phone" class="form-control @error('phone') is-invalid @enderror" value="{{ old('phone') }}">
        @error('phone')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>
    <div class="form-group">
        <label for="date_time">Termín</label>
        <input type="datetime-local" name="date_time" class="form-control @error('date_time') is-invalid @enderror" value="{{ old('date_time') }}">
        @error('date_time')
            <div class="error">{{ $message }}</div>
        @enderror
    </div>

    <div class="form-group">
        <label for="dog_id">Pes</label>
        <select class="form-control" name="dog_id">
            @foreach($dogs as $dog)
                <option value="{{ $dog->id }}">{{ $dog->name }} ({{ $dog->breed }})</option>
            @endforeach
        </select>
    </div>
    
    <div>
        <button type="submit" class="btn btn-blue">Uložit</button>
        <a href="/admin/walks" class="btn btn-white">Zpět</a>
    </div>
</form>
@endsection
