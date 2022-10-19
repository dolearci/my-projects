<!--
|---------------------------------------
|   Autor: Nina Štefeková (xstefe11)
|---------------------------------------
|
-->

@extends('layouts.website')

@section('content')

@if(Session::has('custom_message'))
      <div class="alert alert-custom" role="alert">
          {{ Session::get('custom_message') }}
      </div>
@endif

<div id="datetime">
	<div id="day">
		<h2>Ktorý deň máte voľno?</h2>
		<div class="btn-group btn-group-toggle" data-toggle="buttons">
			<label class="btn btn-custom active">
				<input type="radio" name="day" value="today" autocomplete="off"> Dnes
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="day" value="tomorow" autocomplete="off"> Zajtra
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="day" value="thedayaftertomorow" autocomplete="off"> Pozajtra
			</label>
		</div>
	</div>
	<div id="time">
		<h2>Kedy máte čas?</h2>
		<div class="btn-group btn-group-toggle" data-toggle="buttons">
			<label class="btn btn-custom active">
				<input type="radio" name="time" value="08" autocomplete="off"> 8:00
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="time" value="10" autocomplete="off"> 10:00
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="time" value="12" autocomplete="off"> 12:00
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="time" value="14" autocomplete="off"> 14:00
			</label>
			<label class="btn btn-custom">
				<input type="radio" name="time" value="16" autocomplete="off"> 16:00
			</label>
		</div>
	</div>
</div>

<h2 id="cards-heading">Volní psíkovia:</h2>
<div class="row" id="cards">

</div>

@endsection
