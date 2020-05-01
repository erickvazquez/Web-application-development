const {
  Button,
  colors,
  createMuiTheme,
  CssBaseline,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Icon,
  MuiThemeProvider,
  Typography,
  Paper,
  withStyles,
  main,
  Avatar,
  FormControl,
  InputLabel,
  Input,
  FormControlLabel,
  form,  
  Checkbox,
  green,
  Grow,
} = window['material-ui'];

const styles = theme => ({
    
    

  root: {
    textAlign: 'center',
    paddingTop: theme.spacing.unit * 20,
  },
  icon: {
    marginRight: theme.spacing.unit,
  },
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 490,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    align:'center',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    
  },
  bigAvatar: {
    margin: theme.spacing.unit,
    width: 40,
    height: 40,
    marginBottom: 40,
    
  },

  submit: {
    marginTop: theme.spacing.unit * 7,
    marginBottom: theme.spacing.unit * 6,
  },  
});

class Index extends React.Component {
  constructor(props){
      super(props);
      this.state = {
          email : "",
          id : ""
      }
     
      
  }
  
  
  
  
  leerjson(){
        var xhttp = new XMLHttpRequest();
        var dato = this; 
        xhttp.open("GET", "datos.json", true);
        xhttp.send();
        xhttp.onreadystatechange = function() {
            if(xhttp.readyState === 4 && xhttp.status===200)
            {
                var javaobj= JSON.parse(this.response);
                dato.setState ({
                    email:javaobj.email,
                    id : javaobj.id
                });          
            }
        };      
    }
    
    componentDidMount(){
        fetch("datos.json", {
          method: "GET"
      })
        .then(response => response.json())
        .then(json => {
            console.log(json);
                this.setState ({
                    email:json.email,
                    id : json.id
                });
      });
    }
    
   handleSubmit = event => {
      event.preventDefault;
      window.location.replace("TablaPreguntasAction");
      
  } 


  render() {    
    var { email,id } = this.state;
    const { classes } = this.props;
    
    const mensajeBienvenida = (
        <Typography component="h1" variant="h5">
            Bienvenido, {this.state.email}
        </Typography>
    ); 
    const mensajeExito = (
        <Typography component="h1" variant="h5">
           ¡Inicio de sesión exitoso!  
        </Typography>
    );    
    const icono =(
        <Avatar className={classes.bigAvatar} style={{backgroundColor: '#4CAF50'}}>
            <Icon  fontSize="large">done</Icon>
        </Avatar>
    );
    const boton =(
            <Button
          
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Continuar
          </Button>
    );
    const contenedor =(
        <Paper className={classes.paper}>
            
            <Grow
                in={true}
                style={{ transformOrigin: "0 0 0" }}
                 {...(true ? { timeout: 400 } : {})}
                >
                {icono}
            </Grow>
            <Grow
                in={true}
                style={{ transformOrigin: "0 0 0" }}
                 {...(true ? { timeout: 800 } : {})}
                >
                {mensajeExito}
            </Grow>
            <Grow
                in={true}
                style={{ transformOrigin: "0 0 0" }}
                 {...(true ? { timeout: 700 } : {})}
                >
                {mensajeBienvenida}
            </Grow>
            <Grow
                in={true}
                style={{ transformOrigin: "0 0 0" }}
                 {...(true ? { timeout: 1200 } : {})}
                >
                <form action="TablaPreguntasAction" method = "GET">{boton}</form>
                
            </Grow>
 
        </Paper>
    );
    
    return (
         <main className={classes.main}>
            <CssBaseline />
            {contenedor}
        </main>
    );
  }
}
const Bienvenida = withStyles(styles)(Index);
ReactDOM.render(<Bienvenida />, document.getElementById('root'));