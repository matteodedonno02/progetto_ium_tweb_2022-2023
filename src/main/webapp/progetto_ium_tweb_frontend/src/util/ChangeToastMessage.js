import $ from 'jquery'

export const changeToastMessage = (newMessage) => {
    $(".toast-body").text(newMessage);
    $(".toast-body").addClass("toast-success");
}