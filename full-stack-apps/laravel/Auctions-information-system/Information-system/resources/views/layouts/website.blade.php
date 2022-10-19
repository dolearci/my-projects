<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- CSRF Token -->
        <meta name="csrf-token" content="{{ csrf_token() }}">

        <title>Aukce</title>

        <!-- Styles -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
        <link href="{{ asset('/css/navbar.css') }}" rel="stylesheet"> 
        <link href="{{ asset('/css/auction.css') }}" rel="stylesheet"> 
        <link href="{{ asset('/css/main.css') }}" rel="stylesheet"> 
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.4.0/min/dropzone.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/dropzone/5.4.0/dropzone.js"></script>

    </head>
    <body>
        <x-navbar/>

        <div class="container">
            @yield('content')
        </div>


        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
        <script>
            var CSRF_TOKEN = document.querySelector('meta[name="csrf-token"]').getAttribute("content");
        
            Dropzone.autoDiscover = false;
            var myDropzone = new Dropzone(".dropzone",{ 
                maxFilesize: 3,  // 3 mb
                acceptedFiles: ".jpeg,.jpg,.png,.pdf",
            });
            myDropzone.on("sending", function(file, xhr, formData) {
               formData.append("_token", CSRF_TOKEN);
            }); 
            </script>
    </body>
</html>
