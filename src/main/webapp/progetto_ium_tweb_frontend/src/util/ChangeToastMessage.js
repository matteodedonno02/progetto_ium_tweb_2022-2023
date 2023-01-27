import $ from 'jquery'

export const changeToastMessage = (newMessage) => {
    $(".toast-body").text(newMessage);
}