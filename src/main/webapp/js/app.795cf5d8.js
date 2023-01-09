(function(){"use strict";var e={6319:function(e,t,i){var o=i(9242),n=i(3396);const a={class:"container"},s={class:"container-content"},r={key:0},l={key:1},c={key:2};function d(e,t,i,o,d,p){const u=(0,n.up)("VerticalBar"),m=(0,n.up)("HomeView"),g=(0,n.up)("SearchView"),v=(0,n.up)("MyRepetitions");return(0,n.wg)(),(0,n.iD)(n.HY,null,[(0,n.Wm)(u,{loggedUser:d.loggedUser,page:d.page,onChangePage:p.changePage},null,8,["loggedUser","page","onChangePage"]),(0,n._)("div",a,[(0,n._)("div",s,["home"===d.page?((0,n.wg)(),(0,n.iD)("div",r,[(0,n.Wm)(m,{loggedUser:d.loggedUser},null,8,["loggedUser"])])):"search"===d.page?((0,n.wg)(),(0,n.iD)("div",l,[(0,n.Wm)(g)])):"my-repetitions"===d.page?((0,n.wg)(),(0,n.iD)("div",c,[(0,n.Wm)(v,{loggedUserEmail:d.loggedUser.email},null,8,["loggedUserEmail"])])):(0,n.kq)("",!0)])]),(0,n._)("button",{onClick:t[0]||(t[0]=(...e)=>p.login&&p.login(...e))},"LOGIN")],64)}var p=i(7387),u=i.n(p),m=i(7139);const g={class:"navbar navbar-expand-lg bg-light"},v={class:"container-fluid"},h={class:"navbar-brand"},b=(0,n._)("button",{class:"navbar-toggler",type:"button","data-bs-toggle":"collapse","data-bs-target":"#navbarNav","aria-controls":"navbarNav","aria-expanded":"false","aria-label":"Toggle navigation"},[(0,n._)("span",{class:"navbar-toggler-icon"})],-1),f={class:"collapse navbar-collapse",id:"navbarNav"},w={class:"navbar-nav"},R={class:"nav-item"},_={key:0,class:"nav-item"},y={key:1,class:"nav-item"};function k(e,t,i,o,a,s){const r=(0,n.up)("BookOpenPageVariantOutlineIcon"),l=(0,n.up)("HomeIcon"),c=(0,n.up)("SearchIcon"),d=(0,n.up)("BookAlertIcon");return(0,n.wg)(),(0,n.iD)("nav",g,[(0,n._)("div",v,[(0,n._)("a",h,[(0,n.Wm)(r,{size:38})]),b,(0,n._)("div",f,[(0,n._)("ul",w,[(0,n._)("li",R,[(0,n._)("a",{class:(0,m.C_)(["nav-link","home"===i.page?"active d-flex flex-column align-items-center":"d-flex flex-column align-items-center"]),onClick:t[0]||(t[0]=e=>this.$emit("change-page","home"))},[(0,n.Wm)(l),(0,n.Uk)(" Home ")],2)]),null!==i.loggedUser?((0,n.wg)(),(0,n.iD)("li",_,[(0,n._)("a",{class:(0,m.C_)(["nav-link","search"===i.page?"active d-flex flex-column align-items-center":"d-flex flex-column align-items-center"]),onClick:t[1]||(t[1]=e=>this.$emit("change-page","search"))},[(0,n.Wm)(c),(0,n.Uk)(" Cerca ")],2)])):(0,n.kq)("",!0),null!==i.loggedUser?((0,n.wg)(),(0,n.iD)("li",y,[(0,n._)("a",{class:(0,m.C_)(["nav-link","my-repetitions"===i.page?"active d-flex flex-column align-items-center":"d-flex flex-column align-items-center"]),onClick:t[2]||(t[2]=e=>this.$emit("change-page","my-repetitions"))},[(0,n.Wm)(d),(0,n.Uk)(" Le mie ripetizioni ")],2)])):(0,n.kq)("",!0)])])])])}var D=i(4812),C=i(7148),x=i(3843),U=i(1131),P={name:"VerticalBar",components:{HomeIcon:D.Z,SearchIcon:C.Z,BookOpenPageVariantOutlineIcon:x.Z,BookAlertIcon:U.Z},props:["page","loggedUser"]},T=i(89);const W=(0,T.Z)(P,[["render",k]]);var z=W;const O=(0,n._)("nav",{"aria-label":"breadcrumb"},[(0,n._)("ol",{class:"breadcrumb"},[(0,n._)("li",{class:"breadcrumb-item active","aria-current":"page"},"Home")])],-1),S=(0,n._)("h2",null,"Corsi più richiesti!",-1),M={key:0,class:"cards"},q={key:1,class:"cards"},I=(0,n._)("h2",{class:"pt-5"},"Docenti più richiesti!",-1),Z={key:2,class:"cards"},j={class:"cards"};function H(e,t,i,o,a,s){const r=(0,n.up)("LoadingCard"),l=(0,n.up)("CourseCard"),c=(0,n.up)("ProfessorCard");return(0,n.wg)(),(0,n.iD)(n.HY,null,[O,S,0===a.mostRequestedCourse.length?((0,n.wg)(),(0,n.iD)("div",M,[(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"})])):((0,n.wg)(),(0,n.iD)("div",q,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.mostRequestedCourse,(e=>((0,n.wg)(),(0,n.iD)("div",{class:"course-card",key:e.idCourse},[(0,n.Wm)(l,{loggedUser:i.loggedUser,iconUrl:e.iconUrl,title:e.title},null,8,["loggedUser","iconUrl","title"])])))),128))])),I,0===a.mostRequestedCourse.length?((0,n.wg)(),(0,n.iD)("div",Z,[(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"}),(0,n.Wm)(r,{class:"course-card"})])):(0,n.kq)("",!0),(0,n._)("div",j,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.mostRequestedProfessor,(e=>((0,n.wg)(),(0,n.iD)("div",{class:"professor-card",key:e.serialNumber},[(0,n.Wm)(c,{serialNumber:e.serialNumber,imageUrl:e.imageUrl,title:e.name+" "+e.surname},null,8,["serialNumber","imageUrl","title"])])))),128))])],64)}const N={class:"card"},E={class:"row"},L=["src"],A={class:"card-body"},V={class:"card-title"},Y={key:0},B=(0,n._)("a",{href:"#",class:"btn btn-primary shadow-none border-0"},"Prenota",-1),$=[B];function G(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",N,[(0,n._)("div",E,[(0,n._)("img",{src:i.iconUrl,class:"card-img-top",alt:"Course Card"},null,8,L)]),(0,n._)("div",A,[(0,n._)("h5",V,(0,m.zw)(i.title),1),null!==i.loggedUser?((0,n.wg)(),(0,n.iD)("div",Y,$)):(0,n.kq)("",!0)])])}var K={name:"CourseCard",props:["loggedUser","iconUrl","title"]};const F=(0,T.Z)(K,[["render",G]]);var J=F;const Q={class:"card is-loading",style:{width:"18rem"}},X=(0,n._)("div",{class:"row"},[(0,n._)("div",{class:"image"})],-1),ee=(0,n._)("div",{class:"card-body"},[(0,n._)("h5",{class:"card-title"})],-1),te=[X,ee];function ie(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",Q,te)}var oe={name:"LoadingCard",props:["iconUrl","title"]};const ne=(0,T.Z)(oe,[["render",ie]]);var ae=ne;const se={class:"card",style:{width:"18rem"}},re={class:"card-body"},le={class:"row"},ce=["id"],de={class:"col-7 d-flex flex-column justify-content-center"},pe={class:"card-title"},ue=(0,n._)("br",null,null,-1);function me(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",se,[(0,n._)("div",re,[(0,n._)("div",le,[(0,n._)("div",{id:i.serialNumber,class:"col-5 col-img"},null,8,ce),(0,n._)("div",de,[(0,n._)("h5",pe,[(0,n.Uk)((0,m.zw)(i.title.split(" ")[0])+" ",1),ue,(0,n.Uk)(" "+(0,m.zw)(i.title.split(" ")[1]),1)])])])])])}var ge={name:"ProfessorCard",props:["serialNumber","imageUrl","title"],mounted(){u()("#"+this.serialNumber).css("background-image","url("+this.imageUrl+")")}};const ve=(0,T.Z)(ge,[["render",me]]);var he=ve,be={name:"HomeView",data(){return{mostRequestedCourse:[],mostRequestedProfessor:[]}},props:["loggedUser"],components:{CourseCard:J,LoadingCard:ae,ProfessorCard:he},methods:{getMostRequestedCourse(){let e=this;u().ajax("CourseServlet?operation=mostRequested",{method:"GET",success:t=>{setTimeout((()=>{e.mostRequestedCourse=t}),2e3)}})},getMostRequestedProfessor(){let e=this;u().ajax("ProfessorServlet?operation=mostRequested",{method:"GET",success:t=>{setTimeout((()=>{e.mostRequestedProfessor=t}),2e3)}})}},mounted(){this.getMostRequestedCourse(),this.getMostRequestedProfessor()}};const fe=(0,T.Z)(be,[["render",H]]);var we=fe;const Re=(0,n._)("nav",{"aria-label":"breadcrumb"},[(0,n._)("ol",{class:"breadcrumb"},[(0,n._)("li",{class:"breadcrumb-item active","aria-current":"page"},"Cerca")])],-1),_e={key:0},ye={class:"navigation"},ke={class:"row w-100"},De={class:"col text-center"},Ce={class:"col text-center"},xe={class:"col text-center"},Ue={key:0},Pe={key:1};function Te(e,t,i,o,a,s){const r=(0,n.up)("ArrowLeftThickIcon"),l=(0,n.up)("ArrowRightThickIcon"),c=(0,n.up)("RepetitionTable");return(0,n.wg)(),(0,n.iD)(n.HY,null,[Re,null!==a.availableRepetitions?((0,n.wg)(),(0,n.iD)("div",_e,[(0,n._)("div",ye,[(0,n._)("div",ke,[(0,n._)("div",De,[(0,n._)("div",{class:"left-arrow d-none pt-1",onClick:t[0]||(t[0]=(...e)=>s.previousPage&&s.previousPage(...e))},[(0,n.Wm)(r)])]),(0,n._)("div",Ce,[(0,n._)("h3",null,(0,m.zw)(s.formatDate(Object.keys(a.availableRepetitions)[a.tablePage])),1)]),(0,n._)("div",xe,[(0,n._)("div",{class:"right-arrow pt-1",onClick:t[1]||(t[1]=(...e)=>s.nextPage&&s.nextPage(...e))},[(0,n.Wm)(l)])])])]),((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.availableRepetitions,((e,t)=>((0,n.wg)(),(0,n.iD)("div",{key:t},[t===Object.keys(a.availableRepetitions)[a.tablePage]?((0,n.wg)(),(0,n.iD)("div",Ue,[(0,n.Wm)(c,{repetitions:e,class:"repetition-table"},null,8,["repetitions"])])):((0,n.wg)(),(0,n.iD)("div",Pe,[(0,n.Wm)(c,{repetitions:e,class:"repetition-table d-none"},null,8,["repetitions"])]))])))),128))])):(0,n.kq)("",!0)],64)}const We={class:"table-responsive"},ze={class:"table"},Oe=(0,n._)("thead",null,[(0,n._)("tr",null,[(0,n._)("th",null,"Teacher"),(0,n._)("th",null,"Course"),(0,n._)("th",null,"Time"),(0,n._)("th",null,"Prenota")])],-1);function Se(e,t,i,o,a,s){const r=(0,n.up)("CheckBoldVue");return(0,n.wg)(),(0,n.iD)("div",We,[(0,n._)("table",ze,[Oe,(0,n._)("tbody",null,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(i.repetitions,(e=>((0,n.wg)(),(0,n.iD)("tr",{key:e.idRepetition},[(0,n._)("td",null,(0,m.zw)(e.teaching.professor.name)+" "+(0,m.zw)(e.teaching.professor.surname),1),(0,n._)("td",null,(0,m.zw)(e.teaching.course.title),1),(0,n._)("td",null,(0,m.zw)(e.time),1),(0,n._)("td",null,[(0,n.Wm)(r)])])))),128))])])])}var Me=i(4703),qe={name:"RepetitionTable",props:["repetitions","icon"],components:[Me.Z]};const Ie=(0,T.Z)(qe,[["render",Se]]);var Ze=Ie,je=i(5810),He=i(361);const Ne=e=>{const t=["Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"];return e.split("-")[2]+" "+t[parseInt(e.split("-")[1])-1]+" "+e.split("-")[0]},Ee=e=>{if(-1!==e.indexOf("PM")){const t=e.replace("PM","");return parseInt(t.split(":")[0])+12+":"+t.split(":")[1]}const t=e.replace("AM","");return t.split(":")[0]+":"+t.split(":")[1]};var Le={name:"SearchView",data(){return{availableRepetitions:null,tablePage:0}},components:{RepetitionTable:Ze,ArrowLeftThickIcon:je.Z,ArrowRightThickIcon:He.Z},methods:{formatDate:Ne,getAvailableRepetitions(){let e=this;u().ajax("RepetitionServlet",{method:"GET",data:{operation:"available"},success:t=>{e.availableRepetitions=t}})},nextPage(){this.tablePage++,this.tablePage>0&&u()(".left-arrow").removeClass("d-none"),this.tablePage===Object.keys(this.availableRepetitions).length-1&&u()(".right-arrow").addClass("d-none")},previousPage(){this.tablePage--,this.tablePage<Object.keys(this.availableRepetitions).length-1&&u()(".right-arrow").removeClass("d-none"),0===this.tablePage&&u()(".left-arrow").addClass("d-none")},changeTable(){u()(".d-none")[0].classList.remove("d-none"),u()(".repetition-table")[this.tablePage].classList.add("d-none")}},mounted(){this.getAvailableRepetitions()}};const Ae=(0,T.Z)(Le,[["render",Te]]);var Ve=Ae;const Ye=(0,n._)("nav",{"aria-label":"breadcrumb"},[(0,n._)("ol",{class:"breadcrumb"},[(0,n._)("li",{class:"breadcrumb-item active","aria-current":"page"},"Le mie ripetizioni")])],-1),Be=(0,n._)("h4",{class:"pt-5"},"Ripetizioni da effettuare",-1),$e={key:0},Ge={key:1},Ke={key:0},Fe={key:1},Je={key:0},Qe=(0,n._)("h4",{class:"pt-5"},"Ripetizioni confermate",-1),Xe={key:2},et={key:3},tt={key:0},it={key:1},ot={key:0},nt=(0,n._)("h4",{class:"pt-5"},"Ripetizioni cancellate",-1),at={key:4},st={key:5},rt={key:0},lt={key:1},ct={key:0};function dt(e,t,i,o,a,s){const r=(0,n.up)("CustomToast"),l=(0,n.up)("MyRepetitionCardLoading"),c=(0,n.up)("MyRepetitionCard");return(0,n.wg)(),(0,n.iD)(n.HY,null,[(0,n.Wm)(r),Ye,Be,null===a.pendingRepetitions?((0,n.wg)(),(0,n.iD)("div",$e,[(0,n.Wm)(l),(0,n.Wm)(l),(0,n.Wm)(l)])):((0,n.wg)(),(0,n.iD)("div",Ge,[0===a.pendingRepetitions.length?((0,n.wg)(),(0,n.iD)("div",Ke," Non hai ripetizioni da effettuare ")):((0,n.wg)(),(0,n.iD)("div",Fe,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.pendingRepetitions,(e=>((0,n.wg)(),(0,n.iD)("div",{key:e.idRepetition},[0===e.state?((0,n.wg)(),(0,n.iD)("div",Je,[(0,n.Wm)(c,{repetition:e,onConfirmRepetition:s.moveRepetitionToConfirmedRepetitions,onDeleteRepetition:s.moveRepetitionToDeletedRepetitions},null,8,["repetition","onConfirmRepetition","onDeleteRepetition"])])):(0,n.kq)("",!0)])))),128))]))])),Qe,null===a.confirmedRepetitions?((0,n.wg)(),(0,n.iD)("div",Xe,[(0,n.Wm)(l),(0,n.Wm)(l),(0,n.Wm)(l)])):((0,n.wg)(),(0,n.iD)("div",et,[0===a.confirmedRepetitions.length?((0,n.wg)(),(0,n.iD)("div",tt," Non hai ripetizioni confermate ")):((0,n.wg)(),(0,n.iD)("div",it,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.confirmedRepetitions,(e=>((0,n.wg)(),(0,n.iD)("div",{key:e.idRepetition},[1===e.state?((0,n.wg)(),(0,n.iD)("div",ot,[(0,n.Wm)(c,{repetition:e},null,8,["repetition"])])):(0,n.kq)("",!0)])))),128))]))])),nt,null===a.deletedRepetitions?((0,n.wg)(),(0,n.iD)("div",at,[(0,n.Wm)(l),(0,n.Wm)(l),(0,n.Wm)(l)])):((0,n.wg)(),(0,n.iD)("div",st,[0===a.deletedRepetitions.length?((0,n.wg)(),(0,n.iD)("div",rt," Non hai ripetizioni cancellate ")):((0,n.wg)(),(0,n.iD)("div",lt,[((0,n.wg)(!0),(0,n.iD)(n.HY,null,(0,n.Ko)(a.deletedRepetitions,(e=>((0,n.wg)(),(0,n.iD)("div",{key:e.idRepetition},[2===e.state?((0,n.wg)(),(0,n.iD)("div",ct,[(0,n.Wm)(c,{repetition:e},null,8,["repetition"])])):(0,n.kq)("",!0)])))),128))]))]))],64)}i(7658);const pt={class:"w-100 d-flex align-items-center"},ut={key:0,class:"trash-icon mb-3"},mt={class:"card my-repetition-card me-5"},gt={class:"card-body"},vt={class:"card-title"},ht={class:"col"},bt={class:"row"},ft={class:"col text-start"},wt={class:"card-text"},Rt={class:"col text-end"},_t={key:0},yt=(0,n._)("span",{class:"fw-bold"},"Prenotata",-1),kt=[yt],Dt={key:1},Ct=(0,n._)("span",{class:"fw-bold"},"Effettuata",-1),xt=[Ct],Ut={key:2},Pt=(0,n._)("span",{class:"fw-bold"},"Cancellata",-1),Tt=[Pt],Wt=["data-bs-target"];function zt(e,t,i,o,a,s){const r=(0,n.up)("CustomModal"),l=(0,n.up)("DeleteIcon"),c=(0,n.up)("CheckIcon");return(0,n.wg)(),(0,n.iD)(n.HY,null,[(0,n.Wm)(r,{onConfirmRepetition:t[0]||(t[0]=e=>this.$emit("confirm-repetition",this.repetition)),modalId:"modalConfirm"+i.repetition.idRepetition,newState:"1",operation:"confirmRepetition",title:"Desideri segnare come effettuata la seguente ripetizione ?",repetition:i.repetition},null,8,["modalId","repetition"]),(0,n.Wm)(r,{onDeleteRepetition:t[1]||(t[1]=e=>this.$emit("delete-repetition",this.repetition)),modalId:"modalDelete"+i.repetition.idRepetition,newState:"2",operation:"deleteRepetition",title:"Desideri segnare come cancellata la seguente ripetizione ?",repetition:i.repetition},null,8,["modalId","repetition"]),(0,n._)("div",pt,[0===i.repetition.state?((0,n.wg)(),(0,n.iD)("div",ut,[(0,n.Wm)(l,{"data-bs-toggle":"modal","data-bs-target":"#modalDelete"+i.repetition.idRepetition},null,8,["data-bs-target"])])):(0,n.kq)("",!0),(0,n._)("div",{onContextmenu:t[2]||(t[2]=(...t)=>e.openMenu&&e.openMenu(...t)),class:"w-100 card-row d-flex align-items-center mb-3"},[(0,n._)("div",mt,[(0,n._)("div",gt,[(0,n._)("h5",vt,(0,m.zw)(i.repetition.teaching.course.title),1),(0,n._)("div",ht,[(0,n._)("div",bt,[(0,n._)("div",ft,[(0,n._)("p",wt," Ripetizione con "+(0,m.zw)(i.repetition.teaching.professor.name)+" "+(0,m.zw)(i.repetition.teaching.professor.surname)+" il giorno "+(0,m.zw)(s.formatDate(i.repetition.date))+" alle "+(0,m.zw)(s.formatTime(i.repetition.time)),1)]),(0,n._)("div",Rt,[0===i.repetition.state?((0,n.wg)(),(0,n.iD)("div",_t,kt)):1===i.repetition.state?((0,n.wg)(),(0,n.iD)("div",Dt,xt)):2===i.repetition.state?((0,n.wg)(),(0,n.iD)("div",Ut,Tt)):(0,n.kq)("",!0)])])])])])],32),0===i.repetition.state?((0,n.wg)(),(0,n.iD)("div",{key:1,class:"mb-3","data-bs-toggle":"modal","data-bs-target":"#modalConfirm"+i.repetition.idRepetition},[(0,n.Wm)(c)],8,Wt)):(0,n.kq)("",!0)])],64)}var Ot=i(5624),St=i(5921);const Mt=["id"],qt={class:"modal-dialog modal-dialog-centered"},It={class:"modal-content"},Zt={class:"modal-header"},jt={class:"modal-title fs-5",id:"exampleModalLabel"},Ht=(0,n._)("button",{type:"button",class:"btn-close","data-bs-dismiss":"modal","aria-label":"Close"},null,-1),Nt={class:"modal-body"},Et={class:"modal-footer"},Lt=(0,n._)("button",{type:"button",class:"btn btn-secondary","data-bs-dismiss":"modal"},"Chiudi",-1);function At(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",{id:i.modalId,class:"modal fade",tabindex:"-1","aria-hidden":"true"},[(0,n._)("div",qt,[(0,n._)("div",It,[(0,n._)("div",Zt,[(0,n._)("h1",jt,(0,m.zw)(i.title),1),Ht]),(0,n._)("div",Nt," Ripetizione di "+(0,m.zw)(i.repetition.teaching.course.title)+" con "+(0,m.zw)(i.repetition.teaching.professor.name)+" "+(0,m.zw)(i.repetition.teaching.professor.surname)+" del giorno "+(0,m.zw)(s.formatDate(i.repetition.date))+" alle "+(0,m.zw)(s.formatTime(i.repetition.time)),1),(0,n._)("div",Et,[Lt,(0,n._)("button",{onClick:t[0]||(t[0]=(...e)=>s.executeOperation&&s.executeOperation(...e)),type:"button",class:"btn btn-primary","data-bs-dismiss":"modal"},"Ok")])])])],8,Mt)}var Vt=i(2166),Yt={name:"CustomModal",props:["title","repetition","modalId","newState"],methods:{formatDate:Ne,formatTime:Ee,openToast(){const e=u()("#liveToast"),t=new Vt.FN(e);t.show()},executeOperation(){let e=this;u().ajax("RepetitionServlet",{method:"POST",data:{operation:"edit",idRepetition:e.repetition.idRepetition,newState:e.newState},xhrFields:{withCredentials:!0},crossDomain:!0,success(){"1"===e.newState?e.$emit("confirm-repetition"):"2"===e.newState&&e.$emit("delete-repetition"),e.openToast()}})}}};const Bt=(0,T.Z)(Yt,[["render",At]]);var $t=Bt,Gt={name:"MyRepetitionCard",props:["repetition"],components:{CustomModal:$t,CheckIcon:Ot.Z,DeleteIcon:St.Z},emits:["delete-repetition","confirm-repetition"],methods:{formatDate:Ne,formatTime:Ee,moveRepetitionToDeletedRepetitions(){this.$emit("delete-repetition",this.repetition)}}};const Kt=(0,T.Z)(Gt,[["render",zt]]);var Ft=Kt;const Jt={class:"card my-repetition-card mb-3 is-loading"},Qt=(0,n.uE)('<div class="card-body row"><div class="col text-start"><h5 class="card-title"></h5></div><div class="col text-start"><p class="card-text"></p></div></div>',1),Xt=[Qt];function ei(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",Jt,Xt)}var ti={name:"MyRepetitionCardLoading",props:["title","professor","date","time"],methods:{formatDate:Ne,formatTime:Ee}};const ii=(0,T.Z)(ti,[["render",ei]]);var oi=ii;const ni={id:"liveToast",class:"m-2 toast align-items-center text-bg-primary border-0 position-absolute bottom-0 end-0",role:"alert","aria-live":"assertive","aria-atomic":"true"},ai=(0,n._)("div",{class:"d-flex"},[(0,n._)("div",{class:"toast-body"}," Operazione completata con successo! "),(0,n._)("button",{type:"button",class:"btn-close btn-close-white me-2 m-auto","data-bs-dismiss":"toast","aria-label":"Close"})],-1),si=[ai];function ri(e,t,i,o,a,s){return(0,n.wg)(),(0,n.iD)("div",ni,si)}var li={name:"CustomToast"};const ci=(0,T.Z)(li,[["render",ri]]);var di=ci,pi={name:"MyRepetition",props:["loggedUserEmail"],data(){return{loggedUserRepetitions:null,pendingRepetitions:null,confirmedRepetitions:null,deletedRepetitions:null,todayRepetitions:null}},components:{MyRepetitionCard:Ft,MyRepetitionCardLoading:oi,CustomToast:di},computed:{deletedRepetitionsComputed(){return this.dele}},methods:{getUserRepetitions(e){let t=this;u().ajax("RepetitionServlet",{method:"GET",data:{operation:"selectByEmail",email:e},xhrFields:{withCredentials:!0},crossDomain:!0,success:e=>{setTimeout((()=>{t.loggedUserRepetitions=e,t.loadRepetitionsByState(e)}),2e3)}})},loadRepetitionsByState(e){this.pendingRepetitions=[],e.forEach((e=>{0===e.state&&this.pendingRepetitions.push(e)})),this.confirmedRepetitions=[],e.forEach((e=>{1===e.state&&this.confirmedRepetitions.push(e)})),this.deletedRepetitions=[],e.forEach((e=>{2===e.state&&this.deletedRepetitions.push(e)}))},moveRepetitionToDeletedRepetitions(e){this.pendingRepetitions.pop(e),e.state=2,this.deletedRepetitions.push(e)},moveRepetitionToConfirmedRepetitions(e){this.pendingRepetitions.pop(e),e.state=1,this.confirmedRepetitions.push(e)}},mounted(){this.getUserRepetitions(this.loggedUserEmail)}};const ui=(0,T.Z)(pi,[["render",dt]]);var mi=ui,gi={name:"App",data(){return{page:"home",loggedUser:null,userRepetitions:[]}},components:{VerticalBar:z,HomeView:we,SearchView:Ve,MyRepetitions:mi},methods:{changePage(e){this.page=e},login(){let e=this;u().ajax("UserServlet",{method:"POST",data:{operation:"login",email:"matteodedonno@gmail.com",password:"matteo"},xhrFields:{withCredentials:!0},crossDomain:!0,success:t=>{console.log(t),e.loggedUser=t}})}},mounted(){}};const vi=(0,T.Z)(gi,[["render",d]]);var hi=vi;(0,o.ri)(hi).mount("#app")}},t={};function i(o){var n=t[o];if(void 0!==n)return n.exports;var a=t[o]={exports:{}};return e[o].call(a.exports,a,a.exports,i),a.exports}i.m=e,function(){var e=[];i.O=function(t,o,n,a){if(!o){var s=1/0;for(d=0;d<e.length;d++){o=e[d][0],n=e[d][1],a=e[d][2];for(var r=!0,l=0;l<o.length;l++)(!1&a||s>=a)&&Object.keys(i.O).every((function(e){return i.O[e](o[l])}))?o.splice(l--,1):(r=!1,a<s&&(s=a));if(r){e.splice(d--,1);var c=n();void 0!==c&&(t=c)}}return t}a=a||0;for(var d=e.length;d>0&&e[d-1][2]>a;d--)e[d]=e[d-1];e[d]=[o,n,a]}}(),function(){i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,{a:t}),t}}(),function(){i.d=function(e,t){for(var o in t)i.o(t,o)&&!i.o(e,o)&&Object.defineProperty(e,o,{enumerable:!0,get:t[o]})}}(),function(){i.g=function(){if("object"===typeof globalThis)return globalThis;try{return this||new Function("return this")()}catch(e){if("object"===typeof window)return window}}()}(),function(){i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)}}(),function(){i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})}}(),function(){var e={143:0};i.O.j=function(t){return 0===e[t]};var t=function(t,o){var n,a,s=o[0],r=o[1],l=o[2],c=0;if(s.some((function(t){return 0!==e[t]}))){for(n in r)i.o(r,n)&&(i.m[n]=r[n]);if(l)var d=l(i)}for(t&&t(o);c<s.length;c++)a=s[c],i.o(e,a)&&e[a]&&e[a][0](),e[a]=0;return i.O(d)},o=self["webpackChunkprogetto_ium_tweb_frontend"]=self["webpackChunkprogetto_ium_tweb_frontend"]||[];o.forEach(t.bind(null,0)),o.push=t.bind(null,o.push.bind(o))}();var o=i.O(void 0,[998],(function(){return i(6319)}));o=i.O(o)})();
//# sourceMappingURL=app.795cf5d8.js.map