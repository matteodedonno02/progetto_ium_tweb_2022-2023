<template>
    <div id="liveToast"
        :class="type === 'error' ? 'error-toast m-2 toast align-items-center text-bg-primary border-0 position-absolute bottom-0 end-0' : 'm-2 toast align-items-center text-bg-primary border-0 position-absolute bottom-0 end-0'"
        role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"
                aria-label="Close"></button>
        </div>
    </div>
</template>

<script>
export default {
    name: "CustomToast",
    props: ["type"]
}
</script>