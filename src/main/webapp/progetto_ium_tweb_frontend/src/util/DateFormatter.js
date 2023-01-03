export const formatDate = (date) => {
    const monthNames = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
        "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
    ];

    return date.split("-")[2] + " " + monthNames[parseInt(date.split("-")[1]) - 1] + " " + date.split("-")[0]
}

export const formatTime = (time) => {
    if (time.indexOf("PM") !== -1) {
        const newTime = time.replace("PM", "")
        return (parseInt(newTime.split(":")[0]) + 12) + ":" + newTime.split(":")[1]
    }

    const newTime = time.replace("AM", "")
    return newTime.split(":")[0] + ":" + newTime.split(":")[1]
}