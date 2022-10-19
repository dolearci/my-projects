@extends('layouts.website')

@section('content')
<h1>Uživatelé</h1>
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
            <th scope="col">Role</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        @foreach ($users as $user)
        <tr>
            <td>{{ $user->id }}</td>
            <td><a href="/users/{{ $user->id }}">{{ $user->name }}</a></td>
            <td>{{ $user->email }}</td>
            <td>{{ $user->role }}</td>
            <td class="text-right">
                <div class="btn-group" role="group">
                    <a href="/users/{{ $user->id }}/edit" class="btn">Upravit</a>
                    <form action="/users/{{ $user->id }}" method="POST">
                        @csrf
                        {{ method_field('DELETE') }}
                        <button type="submit" class="btn"
                            onclick="return confirm('Opravdu chcete smazat tohoto uživatele?')">Smazat</button>
                    </form>
                </div>
            </td>
        </tr>
        @endforeach
    </tbody>
</table>

{{-- <div class="pagination-inline">{{ $users->links() }}</div> --}}

@endsection
