const {
  Avatar,
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
  Typography,
  Input,
  FormControl,
  InputLabel,
  Checkbox,
  FormControlLabel
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
   avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
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
    width: '60%',
    color: theme.palette.text.secondary,
    overflowX: 'auto',
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
          preguntas:[],
          check:[],
          idexamen : ""
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

  
  handleSubmit = event => {
      event.preventDefault;
      const data = this.state;
      console.log(data);
      
  }
  handleInputChange = (event) => {
       event.preventDefault;
       this.setState({
           [event.target.name] : event.target.value
       });
  }
  render() {
    const { classes } = this.props;
    return (
    <center>        
    <Paper className={classes.paper}>
        <br/>
        <center>
         <Avatar className={classes.avatar}>
            <Icon>school</Icon>
        </Avatar>
        </center>
       
        <Typography component="h1" variant="h5">
        <center>Nuevo examen</center>
        </Typography>
        <br /> <br/>
        <form action = "EscribirExamenAction2" method = "post" className={classes.form} onSubmit={this.handleSubmit}>
          <input type='hidden' id='tipo' name='tipo' />
          <FormControl margin="normal" required fullWidth>
            <InputLabel>Id de Examen</InputLabel>
            <Input id="id" name="idexamen" autoFocus value = {this.state.idexamen} onChange = {this.handleInputChange}/>
          </FormControl>
         <br/>
      <Paper>
      <Table className={classes.table} >
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
                   <FormControlLabel
                    control={
                        <Checkbox
                            checked={this.state.check[row]}
                            name="NombrePregunta"
                            value={row}
                        />
                     }
                    
                    />
                    <IconButton aria-label="Ver Pregunta" className={classes.margin}> 
                        <Icon className={classes.icon}>visibility</Icon> 
                    </IconButton>  
                   
                </CustomTableCell>
              </TableRow>
            );
    
          })}
        </TableBody>
      </Table>
      </Paper>
      <br/>
      <Button
          
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={event => this.handleSubmit(event)}
         >
            Agregar
       </Button>
     
       <a href="TablaExamenes.jsp">
            <Button fullWidth variant="outlined" color="secondary">Regresar</Button>
        </a>
    </form>
    </Paper>
    </center>
  );
  }
};

const Tabla = withStyles(styles)(Index);
ReactDOM.render(<Tabla />, document.getElementById('root'));

