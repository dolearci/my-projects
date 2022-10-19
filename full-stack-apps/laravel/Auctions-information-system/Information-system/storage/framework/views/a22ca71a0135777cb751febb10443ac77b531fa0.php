

<?php $__env->startSection('content'); ?>





<?php
$rules = array("otevřená","uzavřená");
$types = array("nabídková", "poptávková");
?>

<?php if(Auth::user()->id == $auction->author->id): ?>
<form action="/auctions/store/<?php echo e(Auth::user()->id); ?>" method="POST" id="auction-form">
    <?php echo csrf_field(); ?>
    <div class="form-group">
        <label for="title">Název</label>
        <input type="text" name="title" class="form-control <?php $__errorArgs = ['name'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" value="<?php echo e($auction->title); ?>">
        <?php $__errorArgs = ['title'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>
    <div class="form-group">
        <label for="category">Kategorie</label>
        <select class="form-control" id="category" name="category">
            
            
            <option value="<?php echo e($auction->category); ?>"><?php echo e($auction->category); ?></option>
        </select>
        <?php $__errorArgs = ['category'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control <?php $__errorArgs = ['description'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" name="description" rows="5"><?php echo e($auction->description); ?></textarea>
        <?php $__errorArgs = ['condescriptiontent'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>
    <div class="form-group">
        <label for="type">Typ aukce</label>
        <select class="form-control" id="type" name="type">
            <?php $__currentLoopData = $types; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $type): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                <option value="<?php echo e($type); ?>"><?php echo e($type); ?></option>
            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
        </select>
        <?php $__errorArgs = ['type'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>

    <div class="form-group">
        <label for="rule">Pravidlo</label>
        <select class="form-control" id="rule" name="rule">
            <?php $__currentLoopData = $rules; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $rule): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
                <option value="<?php echo e($rule); ?>"><?php echo e($rule); ?></option>
            <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
        </select>
        <?php $__errorArgs = ['rule'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>

    <div class="form-group">
        <label for="min_cost">Počáteční nabídka</label>
        <input type="number" min="1" name="min_cost" class="form-control <?php $__errorArgs = ['min_cost'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" value="<?php echo e($auction->min_cost); ?>">
        <?php $__errorArgs = ['min_cost'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>

    <div class="form-group">
        <label for="max_cost">Okamžitý nákup</label>
        <input type="number" min="1" name="max_cost" class="form-control <?php $__errorArgs = ['max_cost'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" value="<?php echo e($auction->max_cost); ?>">
        <?php $__errorArgs = ['max_cost'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>

    
    <button type="submit" class="btn btn-primary">Odeslat</button>
</form>

<?php else: ?>
<h1 class="text-center">Schválit aukci</h1>
<form action="/auctions/<?php echo e($auction->id); ?>/approve" method="POST" id="auction-form">
    <?php echo csrf_field(); ?>
    <?php echo e(method_field('PUT')); ?>

    <div class="form-group">
        <label for="title">Název</label>
        <input type="text" name="title" class="form-control" disabled value="<?php echo e($auction->title); ?>">
    </div>
    <div class="form-group">
        <label for="category">Kategorie</label>
        <input type="text" name="category" class="form-control" disabled value="<?php echo e($auction->category->title); ?>">
    </div>
    <div class="form-group">
        <label for="description">Popis</label>
        <textarea class="form-control" name="description" disabled><?php echo e($auction->description); ?></textarea>
    </div>

    <div class="form-group">
        <label for="type">Typ aukce</label>
        <input type="text" name="type" class="form-control" disabled value="<?php echo e($auction->type); ?>">
    </div>

    <div class="form-group">
        <label for="rule">Pravidlo</label>
        <input type="text" name="rule" class="form-control" disabled value="<?php echo e($auction->rule); ?>">
    </div>

    <div class="form-group">
        <label for="min_cost">Počáteční nabídka</label>
        <input type="text" name="min_cost" class="form-control" disabled value="<?php echo e($auction->min_cost); ?>">
    </div>

    <div class="form-group">
        <label for="max_cost">Okamžitý nákup</label>
        <input type="text" name="max_cost" class="form-control" disabled value="<?php echo e($auction->max_cost); ?>">
    </div>

    <div class="form-group">
        <label for="start_at">Začátek aukce</label>
        <input type="datetime-local" name="start_at" class="form-control <?php $__errorArgs = ['start_at'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" value="<?php echo e(old('start_at')); ?>">
        <?php $__errorArgs = ['start_at'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>

    <div class="form-group">
        <label for="end_at">Konec aukce</label>
        <input type="datetime-local" name="end_at" class="form-control <?php $__errorArgs = ['end_at'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?> is-invalid <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>" value="<?php echo e(old('end_at')); ?>">
        <?php $__errorArgs = ['end_at'];
$__bag = $errors->getBag($__errorArgs[1] ?? 'default');
if ($__bag->has($__errorArgs[0])) :
if (isset($message)) { $__messageOriginal = $message; }
$message = $__bag->first($__errorArgs[0]); ?>
            <div class="error"><?php echo e($message); ?></div>
        <?php unset($message);
if (isset($__messageOriginal)) { $message = $__messageOriginal; }
endif;
unset($__errorArgs, $__bag); ?>
    </div>
    <button type="submit" class="btn btn-primary">Schválit</button>
</form>
<form action="/auctions/<?php echo e($auction->id); ?>" method="POST">
    <?php echo csrf_field(); ?>
    <?php echo e(method_field('DELETE')); ?>

    <button type="submit" class="btn"
        onclick="return confirm('Opravdu chcete smazat tohoto uživatele?')">Smazat</button>
</form>
<?php endif; ?>

<?php $__env->stopSection(); ?>
<?php echo $__env->make('layouts.website', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/website/auctions/edit.blade.php ENDPATH**/ ?>