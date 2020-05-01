const {
  Button,
  Icon,
  Typography,
  Paper,
  withStyles,
  Grid,
  ButtonBase,
  Checkbox,
  BottomNavigation,
  BottomNavigationAction,
  Snackbar,
  SnackbarContent,
  IconButton
} = window['material-ui'];

const styles = theme => ({
    
  root: {
    textAlign: 'center',
    paddingTop: theme.spacing.unit * 20,
  },
  icon: {
    margin: theme.spacing.unit,
    fontSize: 32,
  },
  image: {
    position: 'relative',
    maxHeight: 200,
    maxWidth: 500
  },
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    padding: theme.spacing.unit * 2,
    margin: 'auto',
    minWidth: 50,
  },
  button: {
    margin: theme.spacing.unit,
  },
  info: {
    backgroundColor: theme.palette.primary.dark,
    marginBottom: '10%',
  },
  message: {
    display: 'flex',
    alignItems: 'center',
  },
});
class Index extends React.Component {
    
    constructor(props){
      super(props);
      this.state = {
          check:[],
          id: "",
          pregunta: "",
          respuestas:[],
          multimedia: "",
          tipo: "",
          anterior: "",
          siguiente: "",
          aciertos: 0,
          errores: 0,
          correctas: [],
          incorrec: 0,
          correc: 0,
          pregIncorrectas: 0,
          open: false,
          open2: false,
          open3: false,
          respuestaCorrecta: ""
      }
    }
    
    tipodeMultimedia(tipo){
        const { classes } = this.props;
        console.log("Se llamó a la función, tipo es: "+tipo);
        if(tipo==="jpg" || tipo==="png"){
                return (<ButtonBase style={{maxHeight: 'max-content'}} className={classes.image}> <img className={classes.img} src={"multimedia/"+this.state.multimedia} /> </ButtonBase>);
            }else if(tipo==="mp4"){
                return (<video controls> <source src={"multimedia/"+this.state.multimedia} type='video/mp4' /> </video>);
            }else if(tipo==="mp3"){
                return (<audio controls> <source src={"multimedia/"+this.state.multimedia} type='audio/mpeg' /> </audio>);
            }
    }
    
    handleChange = name => event => {
    this.setState({ [name]: event.target.checked });
    }
    
    handleToggle = value => () => {
    const { check } = this.state;
    const currentIndex = check.indexOf(value);
    const newChecked = [...check];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    this.setState({
      check: newChecked,
    });
    }
    
    estaContestado(){
        const {check, respuestas} = this.state;
        for(let i=0;i<check.length;i++)
            if(check[i])
                return true
        
        if(respuestas.length==1)
            return true
        
        this.setState({open: true});
        console.log("Retorna false")
        return false
    }
    
   revisar(){
        const {check, respuestas,correctas} = this.state;
        if(respuestas.length==1){
                console.log("check.length: "+check.length)
                if(check.length>0)
                console.log("check[0]: "+check[0]);
                var checkbox = document.getElementById("checkbox0")
                checkbox.setAttribute("disabled", true)
                
                if((correctas[0]=="true" && check.length==0) || (correctas[0]=="true" && check[0]!=respuestas[0])){
                    console.log("hay un error")
                    var paper = document.getElementById("paperRespuesta0")
                    paper.style.backgroundColor="#ff4d4d"
                    this.setState({respuestaCorrecta: "True"})
                    this.setState({open3: true})
                }else if((correctas[0]=="false" && check[0]==respuestas[0])){
                    console.log("hay un error")
                    var paper = document.getElementById("paperRespuesta0")
                    paper.style.backgroundColor="#ff4d4d"
                    this.setState({respuestaCorrecta: "False"})
                    this.setState({open3: true})
                }else{
                    console.log("hay un acierto")
                    var paper = document.getElementById("paperRespuesta0")
                    paper.style.backgroundColor="#66ff66"
                    this.setState({open2: true})
                }
        }else if(this.estaContestado()){
          console.log("Iluminar las respuestas...");  
            for(let i=0;i<check.length;i++){
                console.log("check["+i+"]: "+check[i]);
                console.log("respuestas.indexOf(check[i]): "+respuestas.indexOf(check[i]));
                console.log("correctas[respuestas.indexOf(check[i])]: "+correctas[respuestas.indexOf(check[i])]);

                if(correctas[respuestas.indexOf(check[i])]==="true"){
                    console.log("hay un acierto")
                    var paper = document.getElementById("paperRespuesta"+respuestas.indexOf(check[i]))
                    paper.style.backgroundColor="#66ff66"
                    this.setState({open2: true})
                }else if(correctas[respuestas.indexOf(check[i])]==="false"){
                    console.log("hay un error")
                    var paper = document.getElementById("paperRespuesta"+respuestas.indexOf(check[i]))
                    paper.style.backgroundColor="#ff4d4d"
                    this.setState({open3: true})
                }


                for(let i=0;i<respuestas.length;i++){

                    if(correctas[i]==="true"){
                        this.setState({respuestaCorrecta: respuestas[i]})
                    }

                    var checkbox = document.getElementById("checkbox"+i)
                    checkbox.setAttribute("disabled", true)

                }
            }
        }else{
          this.setState({open: true});
        }
    }
    
    evaluar= () => {
        var i = 0, c=0, e=0, inc=0, cor=0;
        var dato = this; 
        const { check,correctas,respuestas } = dato.state;
        console.log(check);
        
        for(i=0;i<check.length;i++){
            if(correctas[respuestas.indexOf(check[i])]==="false"){
                e++;
                console.log("Hay un error");
                return (this.state.pregIncorrectas+1);
            }
        }
        
        if(respuestas.length==1){
            if((correctas[0]=="true" && check.length==0) || (correctas[0]=="true" && check[0]!=respuestas[0])){
                e++;
                console.log("Hay un error");
                return (this.state.pregIncorrectas+1);
            }else if((correctas[0]=="false" && check[0]==respuestas[0])){
                e++;
                console.log("Hay un error");
                return (this.state.pregIncorrectas+1);
            }
        }
        
        return this.state.pregIncorrectas;
    };
    
    componentDidMount = () => {
      const {classes} = this.props;
      fetch("preguntamc.json", {
          method: "GET"
      }).then(response => response.json())
        .then( json =>{
            console.log(json);
            var x = json.multimedia.substring(json.multimedia.length -3, json.multimedia.length);
            console.log(x);
            this.setState ({
                id: json.id,
                pregunta: json.pregunta,
                respuestas: json.resp,
                multimedia: json.multimedia,
                anterior: json.anterior,
                siguiente: json.siguiente,
                examen: json.examen,
                correctas: json.corr,
                tipo: x,
                pregIncorrectas: json.errores
      });return x;});
  }
  
  render() {
    const {classes} = this.props;
    return (
     <div>
     <Grid item xs={12}>
      <Paper className={classes.paper}>    
        <Grid container spacing={16} alignItems="center" justify="center">
          <Grid item xs={12} sm container>
            <Grid item xs container direction="column" spacing={16}>
              <Grid item xs>
                <Typography gutterBottom variant="h6">
                  {this.state.id}
                </Typography>
                <Typography variant="h5">{this.state.pregunta}</Typography>
                {this.state.respuestas.map((res, id) => (
                  <Grid container item xs={24} spacing={24}>
                    <Grid item xs={8}>
                      <Paper className={classes.paper} id={"paperRespuesta"+id}>
                        <Grid container direction="row" alignItems="center">
                            <Grid item>
                                <Checkbox 
                                id ={"checkbox"+id}
                                checked={this.state.check.indexOf(res) !== -1}
                                onClick={this.handleToggle(res)}
                                tabIndex={-1}
                                disableRipple
                                color="primary" />
                            </Grid>
                            <Grid item>
                                <Typography variant="body1">{res}</Typography>
                            </Grid>
                        </Grid>
                      </Paper>
                    </Grid>
                  </Grid>
                ))}
              </Grid>
            </Grid>
          </Grid>
          <Grid item justify="center">
              {this.tipodeMultimedia(this.state.tipo)}
          </Grid>
        </Grid>
        <BottomNavigation
          onChange={this.handleChange}
          showLabels
          className={classes.root}
        >
          <BottomNavigationAction label={'Anterior: "'+this.state.anterior+'"'}  onClick = {() => this.enviarPost("EvaluarPreguntaAction2?examen="+this.state.examen+"&pregunta="+this.state.anterior+"&err="+this.state.pregIncorrectas)} icon={ <Icon className={classes.icon}>skip_previous</Icon>  } />
          <BottomNavigationAction label="Evaluar"  onClick = {this.revisar.bind(this)} icon={ <Icon className={classes.icon}>offline_pin</Icon>} />
          <BottomNavigationAction label={'Siguiente: "'+this.state.siguiente+'"'} onClick = {() => {
              if(this.state.siguiente=="Evaluacion del Examen"){
                if(this.estaContestado()){
                console.log("Entra a la funcion")
                window.location.replace("EvaluarExamenAction2?examen="+this.state.examen+"&pregunta="+this.state.siguiente+"&err="+this.evaluar()) 
                }
              }else{
                if(this.estaContestado()){
                console.log("Entra a la funcion")
                window.location.replace("EvaluarPreguntaAction2?examen="+this.state.examen+"&pregunta="+this.state.siguiente+"&err="+this.evaluar()) 
                }
              }}} icon={<Icon className={classes.icon}>skip_next</Icon>} />
        </BottomNavigation>
      </Paper>
        <a href="TablaExamenes.jsp">
                <Button fullWidth variant="contained" color="primary">Regresar </Button>
        </a>
      </Grid>
      <Snackbar style={{marginBottom: '20px'}} autoHideDuration={6000} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}} open={this.state.open} onClose={() => this.setState({open: false})}
                ContentProps={{
                  'aria-describedby': 'message-id',
                  'className': "info"
                }}
                message= {
                <span id="message-id" className={classes.message}>
                <Icon className={classes.icon}>info</Icon>
                  Debes seleccionar al menos un elemento.
                </span>
                }
                action={[ 
                    <IconButton className={classes.icon} key="close" aria-label="Close" color="inherit" onClick={() => this.setState({open: false})}><Icon className={classes.icon}>close</Icon> </IconButton> 
                ]}
            />
        <Snackbar style={{marginBottom: '20px'}} autoHideDuration={6000} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}} open={this.state.open2} onClose={() => this.setState({open2: false})}
                ContentProps={{
                  'aria-describedby': 'message-id',
                  'className': "info"
                }}
                message= {
                <span id="message-id" className={classes.message}>
                <Icon className={classes.icon}>info</Icon>
                  Respondiste correctamente!.
                </span>
                }
                action={[ 
                    <IconButton className={classes.icon} key="close" aria-label="Close" color="inherit" onClick={() => this.setState({open2: false})}><Icon className={classes.icon}>close</Icon> </IconButton> 
                ]}
            />
        <Snackbar style={{marginBottom: '20px'}} autoHideDuration={6000} anchorOrigin={{vertical: 'bottom', horizontal: 'center'}} open={this.state.open3} onClose={() => this.setState({open3: false})}
                ContentProps={{
                  'aria-describedby': 'message-id',
                  'className': "info"
                }}
                message= {
                <span id="message-id" className={classes.message}>
                <Icon className={classes.icon}>info</Icon>
                  Respuesta incorrecta. La respuesta correcta era: {this.state.respuestaCorrecta}.
                </span>
                }
                action={[ 
                    <IconButton className={classes.icon} key="close" aria-label="Close" color="inherit" onClick={() => this.setState({open3: false})}><Icon className={classes.icon}>close</Icon> </IconButton> 
                ]}
            />
     </div>
    );
  }
}
const EvaluarExamen = withStyles(styles)(Index);
ReactDOM.render(<EvaluarExamen />, document.getElementById('root'));