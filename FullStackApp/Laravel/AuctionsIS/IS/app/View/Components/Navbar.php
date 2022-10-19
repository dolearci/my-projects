<?php

namespace App\View\Components;
use App\Category; 
use Illuminate\View\Component;

class Navbar extends Component
{
    public $category_list;
    /**
     * Create a new component instance.
     *
     * @return void
     */
    public function __construct()
    {
        $category_list = Category::get();
        $this->category_list = $category_list;
    }

    /**
     * Get the view / contents that represent the component.
     *
     * @return \Illuminate\View\View|string
     */
    public function render()
    {
        return view('components.navbar');
    }
}
