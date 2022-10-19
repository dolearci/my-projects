<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/"><div class="logo">Logo</div></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
  
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Vyberte si kategorie <span class="sr-only">(current)</span>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">    
                    <a class="dropdown-item" href="/">Všechny kategorie</a>
                    <?php $__currentLoopData = $category_list; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $category): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                    <a class="dropdown-item" href="<?php echo e($category->path()); ?>"><?php echo e($category->title); ?></a>
                    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
                </div>
            </li>
            <!-- <li class="nav-item">
            <a class="nav-link" href="#hot">Žhavé aukce</a>
            </li> -->
            <li class="nav-item">
            <a class="nav-link" href="/auctions/ending">Končící aukce</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="/auctions/new">Nové aukce</a>
            </li>
            <li class="nav-item">
            <a class="nav-link" href="/auctions/upcoming">Budoucí aukce</a>
            </li>
        </ul>
        <form class="form-inline" role="search" method="POST" action="/search">
            <?php echo csrf_field(); ?>
            <div class="form-group">
                <input type="text" class="form-control mr-sm-2" placeholder="Hledat.." name="text">                        
                 <?php if (isset($component)) { $__componentOriginal4f66722947691db01920253e9e2edd1fa3282e1d = $component; } ?>
<?php $component = $__env->getContainer()->make(App\View\Components\CategoryDropdown::class, []); ?>
<?php $component->withName('category-dropdown'); ?>
<?php if ($component->shouldRender()): ?>
<?php $__env->startComponent($component->resolveView(), $component->data()); ?>
<?php $component->withAttributes([]); ?>
<?php if (isset($__componentOriginal4f66722947691db01920253e9e2edd1fa3282e1d)): ?>
<?php $component = $__componentOriginal4f66722947691db01920253e9e2edd1fa3282e1d; ?>
<?php unset($__componentOriginal4f66722947691db01920253e9e2edd1fa3282e1d); ?>
<?php endif; ?>
<?php echo $__env->renderComponent(); ?>
<?php endif; ?> 
            </div>
            <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Hledat</button>
        </form>
        <ul class="navbar-nav mr-left">
            <?php if(auth()->guard()->guest()): ?>
            <li class="nav-item"><a class="nav-link" href="/login">Přihlásit se</a></li>
            <?php else: ?>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <?php echo e(Auth::user()->name); ?>

                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">    
                    <a class="dropdown-item" href="/auctions/my-auctions">Moje aukce</a>
                    <?php if (app(\Illuminate\Contracts\Auth\Access\Gate::class)->check('admin_or_auctioneer')): ?>
                    <a class="dropdown-item" href="/auctions/unapproved">Neschválené aukce</a>
                    <a class="dropdown-item" href="/auctions/approved">Schválené aukce</a>
                    <?php endif; ?>
                    <?php if (app(\Illuminate\Contracts\Auth\Access\Gate::class)->check('admin')): ?>
                    <a class="dropdown-item" href="/users">Spravovat uživatele</a>
                    <?php endif; ?>
                    <a class="dropdown-item" href="<?php echo e(route('logout')); ?>"
                        onclick="event.preventDefault();
                                    document.getElementById('logout-form').submit();">
                        <?php echo e(__('Odhlásit se')); ?>

                    </a>
                    <form id="logout-form" action="<?php echo e(route('logout')); ?>" method="POST" class="d-none">
                        <?php echo csrf_field(); ?>
                    </form>
                </div>
            </li>
            <?php endif; ?>
            <li class="nav-item"><a class="nav-link" href="/auctions/create">Prodat</a></li>
        </ul>
    </div>
</nav><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/components/navbar.blade.php ENDPATH**/ ?>