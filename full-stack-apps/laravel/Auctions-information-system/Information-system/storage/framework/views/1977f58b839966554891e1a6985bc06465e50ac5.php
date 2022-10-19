

<?php $__env->startSection('content'); ?>

<div class="container">
    <div class="row">
    <?php $__currentLoopData = $auction_list; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $auction): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
        <div class="col-xs-8 col-sm-6 col-lg-3">
            
            <a href="/auctions/show/<?php echo e($auction->id); ?>">
                <div class="card">
                    <img class="card-img-top" src="..." alt="Thumbnail">
                    <div class="card-body" style="height: 220px">
                        <h5 class="card-title"><?php echo e($auction->title); ?></h5>
                        <p class="card-text"><?php echo e($auction->type); ?> | <?php echo e($auction->rule); ?></p>
                        <?php if(auth()->guard()->guest()): ?>
                        <p class="card-text"><?php if($auction->rule == "uzavřená"): ?> <?php echo e($auction->min_cost); ?> <?php else: ?> <?php echo e($auction->top_bid()); ?> <?php endif; ?> Kč</p>
                        <?php else: ?>
                        <?php if(Auth::user()->id == $auction->auctioneer_id || Auth::user()->id == $auction->author_id): ?>
                        <p class="card-text"><?php echo e($auction->top_bid()); ?> Kč</p>
                        <?php else: ?> 
                        <p class="card-text"><?php if($auction->rule == "uzavřená"): ?> <?php echo e($auction->min_cost); ?> <?php else: ?> <?php echo e($auction->top_bid()); ?> <?php endif; ?> Kč</p>
                        <?php endif; ?>
                        <?php endif; ?>
                    </div>
                </div>
            </a>
        </div>
    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
    </div>
</div>
    
<?php $__env->stopSection(); ?>
        
        


<?php echo $__env->make('layouts.website', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/website/auctions/index.blade.php ENDPATH**/ ?>