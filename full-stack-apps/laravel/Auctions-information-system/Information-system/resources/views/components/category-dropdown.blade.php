{{-- TODO: categorie které muže prohledavat $category --}}
{{-- TODO: changeTag bude ajax funkce která bude filtrovat data na strance podle tagu kategorie --}}
<select class="browser-default custom-select" name="category" id="category" onchange="changeTag(this.value);">
    <option value="0">Všechny kategorie</option>
    @foreach ($category_list as $category)
    <option value="{{ $category->id }}">{{ $category->title }}</option>
    @endforeach
</select>