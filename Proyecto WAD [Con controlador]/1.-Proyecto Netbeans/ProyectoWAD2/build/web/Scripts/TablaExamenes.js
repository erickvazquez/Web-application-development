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



class Index extends React.Component {
    constructor(props){
      super(props);
      this.state = {
          examenes:[]
      }
  }


enviarPost(accion){
    const form = document.createElement('form');
    form.method = "post";
    form.action = accion;
    document.body.appendChild(form);
    form.submit();
  }

  componentDidMount = () => {
      fetch("examenes.json", {
          method: "GET"
      }).then(response => response.json())
        .then( json =>{
            this.setState ({
            examenes: json.ids
      })})
  }



  handleClick = () => {
    var script = document.createElement("script");
    script.src = 'CrearPregunta.js';
    script.type = 'text/babel';
    document.body.appendChild(script);
    console.log("jala");
  };
  
  eliminar = value => () =>{
    
    if (confirm('¿Estás seguro de borrar este examen?')) {
       this.enviarPost("ControladorPrincipal?accion=BorrarExamenAction&examenEliminado="+value)
    } 
  } 

  render() {
    const { classes } = this.props;
    return (
    <Paper className={classes.paper} >
        <Grid
            container spacing={16}
            justify='flex-end'
          >
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

        <Typography component="h3" variant="h5">
        <center>Creación de Examenes</center>
        </Typography>
        <br /><br />
        <Button variant="contained" color="primary" className={classes.button} size="large" onClick = {() => this.enviarPost("ControladorPrincipal?accion=EscribirExamenAuxAction")}>
            Crear Examen
        <Icon className={classes.icon}>add_circle_outline</Icon>
        </Button>
        <Button variant="contained" color="primary" className={classes.button} size="large" onClick = {() => this.enviarPost("ControladorPrincipal?accion=TablaPreguntasAction")}>
            Creación de Preguntas
        <Icon className={classes.icon}>assignment</Icon>
        </Button>
        <br /><br />
      <Paper>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <CustomTableCell>Examen</CustomTableCell>
            <CustomTableCell>Acciones</CustomTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {this.state.examenes.map(row => {
            return (
              <TableRow className={classes.row}>
                <CustomTableCell component="th" scope="row">
                  {row}
                </CustomTableCell>
                <CustomTableCell>
                    <IconButton aria-label="Evaluar Examen" className={classes.margin} onClick = {() => this.enviarPost("ControladorPrincipal?accion=EvaluarPreguntaAction&err=0&examen="+row)}>
                        <Icon className={classes.icon}>visibility</Icon>
                    </IconButton>
                    <IconButton aria-label="Editar Examen" className={classes.margin} onClick = {() => this.enviarPost("ControladorPrincipal?accion=EditarExamenAction&examen="+row)}>
                        <Icon className={classes.icon}>edit</Icon> 
                    </IconButton>
                    <IconButton  aria-label="Eliminar Examen" className={classes.margin} onClick = {this.eliminar(''+row).bind(this)}>
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