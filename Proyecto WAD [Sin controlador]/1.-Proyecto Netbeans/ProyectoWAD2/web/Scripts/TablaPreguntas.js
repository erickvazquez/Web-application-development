const {
  Button,
  Grid,
  Icon,
  IconButton,
  Paper,
  withStyles,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography
} = window['material-ui'];

const CustomTableCell = withStyles(theme => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
  
  button: {
    margin: theme.spacing.unit,
  },
}))(TableCell);

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
  row: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.background.default,
    },
  },
  icon: {
    margin: theme.spacing.unit,
    fontSize: 32,
  },
  paper: {
    padding: theme.spacing.unit * 2,
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 4,
    height: '100%',
    color: theme.palette.text.secondary,
    overflowX: 'auto'
  },margin: {
    margin: 0,
  },
  button:{
   margin: 5
  }
});

let id = 0;
function pregunta(nombre) {
  id += 1;
  return { id, nombre};
}

const rows = [
  pregunta('Pregunta 1'),
  pregunta('Pregunta 2'),
  pregunta('Pregunta 3'),
  pregunta('Pregunta 4'),
  pregunta('Pregunta 5'),
];
class Index extends React.Component {
    constructor(props){
      super(props);
      this.state = {
          preguntas:[]
      }
  }
  
  leerjson(){
        var xhttp = new XMLHttpRequest();
        var dato = this; 
        xhttp.open("GET", "preguntas.json", true);
        xhttp.send();
        xhttp.onreadystatechange = function() {
            if(xhttp.readyState === 4 && xhttp.status===200)
            {
                var i,javaobj= JSON.parse(this.response);
                console.log(javaobj);
                console.log(javaobj.ids.length);
                for(i=0;i<javaobj.ids.length;i++){
                    console.log(javaobj.ids[i]);
                    dato.setState ({
                    preguntas: javaobj.ids
                });
                    console.log(dato.state.preguntas[i]);
                    console.log("logitud preguntas: "+dato.state.preguntas.length);
                }        
            }
        };      
    }
   eliminar = value => () =>{
    
    if (confirm('¿Estás seguro de borrar esta pregunta?')) {
       window.location.replace("BorrarPreguntaAction?preguntaEliminada="+value)
    } 
  } 
  
  componentDidMount = () => {
      fetch("preguntas.json", {
          method: "GET"
      }).then(response => response.json())
        .then( json =>{
            this.setState ({
            preguntas: json.ids
      })})
  }

  render() {
    const { classes } = this.props;
    return (
            
    <Paper className={classes.paper} >
        
        <Grid container spacing={16} justify='flex-end'>
          <a href="SubirArchivo.jsp" >
            <Button variant="outlined" color="primary" className={classes.button} >
            Subir Archivos
            </Button>
          </a>
          <a href="Inicio.jsp" >
            <Button variant="outlined" color="primary" className={classes.button} >
            Cerrar Sesión
            </Button>
          </a>
         </Grid>
        <br/>
        <Typography component="h3" variant="h5">
        <center>Creación de Preguntas</center>
        </Typography>
        <br /><br />
        <a href="CrearPreguntaTipo.jsp" >
        <Button variant="contained" color="primary" className={classes.button} size="large" >
            Crear Pregunta 
        <Icon className={classes.icon}>add_circle_outline</Icon>
        </Button>
        </a>
        <a href ="TablaExamenesAction">
        <Button variant="contained" color="primary" className={classes.button} size="large" >
            Creación de Exámenes
        <Icon className={classes.icon}>folder</Icon>
        </Button>
        </a>
        <br /><br />
      <Paper>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <CustomTableCell>Pregunta</CustomTableCell>
            <CustomTableCell>Acciones</CustomTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {this.state.preguntas.map(row => {
            return (
              <TableRow className={classes.row}>
                <CustomTableCell component="th" scope="row">
                  {row}
                </CustomTableCell>
                <CustomTableCell> 
                    <IconButton aria-label="Ver Pregunta" className={classes.margin} onClick = {() => window.location.replace("VerPreguntaAction?pregunta="+row)}> 
                        <Icon className={classes.icon}>visibility</Icon> 
                    </IconButton>  
                    <IconButton aria-label="Editar Pregunta" className={classes.margin} onClick = {() => window.location.replace("EditarPreguntaAction?pregunta="+row)}>  
                        <Icon className={classes.icon}>edit</Icon> 
                    </IconButton> 
                    <IconButton aria-label="Borrar Pregunta" className={classes.margin}  onClick = {this.eliminar(''+row)}>  
                        <Icon className={classes.icon}>deleteicon</Icon> 
                    </IconButton> 
                </CustomTableCell>
              </TableRow>
            );
    
          })}
        </TableBody>
      </Table>
      </Paper>
    </Paper>
  );
  }
};

const Tabla = withStyles(styles)(Index);
ReactDOM.render(<Tabla />, document.getElementById('root'));