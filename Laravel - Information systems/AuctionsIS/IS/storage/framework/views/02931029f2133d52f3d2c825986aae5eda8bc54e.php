

<?php $__env->startSection('content'); ?>

<div id="container">
    <?php if(Session::has('custom_message')): ?>
      <div class="alert alert-success" role="alert">
          <?php echo e(Session::get('custom_message')); ?>

      </div>
    <?php endif; ?>
    <!-- Stack the columns on mobile by making one full-width and the other half-width -->
    <div class="row">
        <h3><?php echo e($auction->title); ?></h3>
    </div>
    <div class="row">
      <?php if (app(\Illuminate\Contracts\Auth\Access\Gate::class)->check('author')): ?>
    <a href="/auctions/<?php echo e($auction->id); ?>/edit" class="btn btn-primary">Upravit</a>
      <?php endif; ?>
      <?php if (app(\Illuminate\Contracts\Auth\Access\Gate::class)->any(['auctioneer', 'admin'])): ?>
          <?php if(!$auction->auctioneer): ?>
          <a href="/auctions/<?php echo e($auction->id); ?>/edit" class="btn btn-primary">Schválit</a>
          <?php endif; ?>
      <?php endif; ?>
    </div>
    <div class="row">
        
        <div class="col-12 col-md-6">
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                  <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                  <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                  <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                  <div class="carousel-item active">
                    <img class="d-block w-100" src="..." alt="First slide">
                  </div>
                  <div class="carousel-item">
                    <img class="d-block w-100" src="..." alt="Second slide">
                  </div>
                  <div class="carousel-item">
                    <img class="d-block w-100" src="..." alt="Third slide">
                  </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
                </a>
              </div>
        </div>
        <div class="col-12 col-md-6">
            <div class="row">
                <div class="col-md-7">
                    <p><?php if( $auction->rule == "uzavřená"): ?> Nejnižší možná nabídka <?php else: ?> Aktualní cena <?php endif; ?></p>
                    <h2><?php if( $auction->rule == "uzavřená"): ?> <?php echo e($auction->min_cost); ?> <?php else: ?> <?php echo e($auction->top_bid()); ?> <?php endif; ?> Kč</h2>
                    <p>Přihoďte si</p>
                    <p><?php echo e($auction->rule); ?> aukce</p>
                    <p>Sleduje </p>
                    <p>Končí: <?php echo e($auction->end_at); ?>

                </div>
                <div class="col-md-5">
                    <form action="/offers/<?php echo e($auction->id); ?>/bid" method="post">
                    <?php echo csrf_field(); ?> <!-- <?php echo e(csrf_field()); ?> -->
                        <div class="form-group">
                            <small id="bidHelp" class="form-text text-muted">Kolik chcete za zboží dát?</small>
                            <input type="number" class="form-control" id="bid" name="bid" value="<?php echo e($auction->min_cost); ?>">
                        </div>
                        <button type="submit" class="btn btn-outline-success my-2 my-sm-0">Přihodit</button>
                    </form>
                    <?php if(auth()->guard()->check()): ?>
                    <?php if(Auth::user()->id == $auction->auctioneer_id): ?>
                    <a href="/auction/<?php echo e($auction->id); ?>/signed-users" class="btn btn-primary">Zobrazit registrace</a>
                    <?php elseif(Auth::user()->id != $auction->author_id): ?>
                    <a href="/auction/<?php echo e($auction->id); ?>/sign-up" class="btn btn-primary">Přihlásit se do aukce</a>
                    <?php endif; ?>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h4>Autor: <?php echo e($auction->author->name); ?></h4>
                    <a href="#" class="btn btn-primary">Nabídky prodejce v této kategorii</a>
                    <a href="#" class="btn btn-primary">Všechny nabídky prodejce</a>
                </div>
                <div class="col-md-6">
                    <h4>Organizator: <?php if($auction->auctioneer): ?> <?php echo e($auction->auctioneer->name); ?><?php endif; ?></h4>
                    <a href="#" class="btn btn-primary">Nabídky organizátora v této kategorii</a>
                    <a href="#" class="btn btn-primary">Všechny nabídky organizátora</a>
                </div>
            </div>
            <div>
              <?php $__currentLoopData = $auction->users; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $user): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                <div><?php echo e($user->name); ?>, <?php echo e($user->pivot->status); ?></div>
              <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
            </div>
        </div>
    </div>
    
    <br>
    
</div>
<?php $__env->stopSection(); ?>
<?php echo $__env->make('layouts.website', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/website/auctions/show.blade.php ENDPATH**/ ?>