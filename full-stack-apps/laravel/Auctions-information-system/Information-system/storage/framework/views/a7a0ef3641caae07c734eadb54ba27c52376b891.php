

<?php $__env->startSection('content'); ?>
<h1>Uživatelé</h1>
<?php if(Session::has('custom_message')): ?>
    <div class="alert alert-success" role="alert">
        <?php echo e(Session::get('custom_message')); ?>

    </div>
<?php endif; ?>
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
        <?php $__currentLoopData = $users; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $user): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
        <tr>
            <td><?php echo e($user->id); ?></td>
            <td><a href="/users/<?php echo e($user->id); ?>"><?php echo e($user->name); ?></a></td>
            <td><?php echo e($user->email); ?></td>
            <td><?php echo e($user->role); ?></td>
            <td class="text-right">
                <div class="btn-group" role="group">
                    <a href="/users/<?php echo e($user->id); ?>/edit" class="btn">Upravit</a>
                    <form action="/users/<?php echo e($user->id); ?>" method="POST">
                        <?php echo csrf_field(); ?>
                        <?php echo e(method_field('DELETE')); ?>

                        <button type="submit" class="btn"
                            onclick="return confirm('Opravdu chcete smazat tohoto uživatele?')">Smazat</button>
                    </form>
                </div>
            </td>
        </tr>
        <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
    </tbody>
</table>

<div class="pagination-inline"><?php echo e($users->links()); ?></div>
<a href="/users/create" class="btn btn-primary">Přidat</a>

<?php $__env->stopSection(); ?>

<?php echo $__env->make('layouts.website', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/website/users/index.blade.php ENDPATH**/ ?>