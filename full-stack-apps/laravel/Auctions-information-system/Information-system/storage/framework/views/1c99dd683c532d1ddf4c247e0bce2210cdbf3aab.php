

<select class="browser-default custom-select" name="category" id="category" onchange="changeTag(this.value);">
    <option value="0">Všechny kategorie</option>
    <?php $__currentLoopData = $category_list; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $category): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
    <option value="<?php echo e($category->id); ?>"><?php echo e($category->title); ?></option>
    <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
</select><?php /**PATH C:\School\github projekty\PHP\IIS\app\resources\views/components/category-dropdown.blade.php ENDPATH**/ ?>