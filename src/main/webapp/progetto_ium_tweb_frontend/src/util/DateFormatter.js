export const formatDate = (date) => {
    const monthNames = ["Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
        "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
    ];

    return date.split("-")[2] + " " + monthNames[parseInt(date.split("-")[1]) - 1] + " " + date.split("-")[0]
}

export const sumHours = (date) => {
    return parseInt(date.split(":")[0]) + 1 + ":00"
}

export const formatTime = (time) => {
    if (time.indexOf("PM") !== -1) {
        const newTime = time.replace("PM", "")
        return (parseInt(newTime.split(":")[0]) + 12) + ":" + newTime.split(":")[1]
    }

    const newTime = time.replace("AM", "")
    return newTime.split(":")[0] + ":" + newTime.split(":")[1]
}

// export const fromCalendarDateToString(date) {
//     const month = {"Gennaio": "01", "Febbraio": "02", "Marzo": "03", "Aprile": "04", "Maggio": "05", "Giugno": "06", "Luglio": "07", "Agosto": "08", "Settembre": "09", "Ottobre": "10", "Novembre": "11", "Dicembre": "12"}
//     return date.split(" ")[3] + "-" + month(date.split(" ")[2]) + date.split
// }