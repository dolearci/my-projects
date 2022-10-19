<!--
|---------------------------------------
|   Autor: Daniel Olearčin (xolear00)
|---------------------------------------
|
-->

@extends('layouts.admin')

@section('content')
<h1>Zamestnanci</h1>
@if(Session::has('custom_message'))
    <div class="alert alert-success" role="alert">
        {{ Session::get('custom_message') }}
    </div>
@endif
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Jméno</th>
            <th scope="col">Email</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        @foreach ($users as $user)
        <tr>
            <td>{{ $user->id }}</td>
            <td>{{ $user->name }}</td>
            <td>{{ $user->email }}</td>
            <td class="text-right">
                <div class="btn-group" role="group">
                    <a href="/admin/users/{{ $user->id }}/edit" class="btn btn-white float-left mr-2"><i class="fas fa-pen"></i></a>
                    <form action="/admin/users/{{ $user->id }}" method="POST">
                        @csrf
                        {{ method_field('DELETE') }}
                        <button type="submit" class="btn btn-white"
                            onclick="return confirm('Opravdu chcete smazat tohoto uživatele?')"><i
                                class="fas fa-trash"></i></button>
                    </form>
                </div>
            </td>
        </tr>
        @endforeach
    </tbody>
</table>

<div class="pagination-inline">{{ $users->links() }}</div>
<a href="/admin/users/create" class="btn btn-blue btn-inline">Pridať</a>

@endsection
