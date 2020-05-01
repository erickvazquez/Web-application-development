const {
  Button, colors,createMuiTheme,CssBaseline,Dialog,DialogActions,DialogContent,DialogContentText,
  DialogTitle,Icon,MuiThemeProvider,Typography,Paper,withStyles,main,Avatar,FormControl,
  InputLabel,Input,FormControlLabel,form, Checkbox, Grid, Switch } = window['material-ui'];

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
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    overflowX: 'auto'
    
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },  
});
class Index extends React.Component {
  constructor(props){
      super(props);
      this.state = {
          idpregunta : "",
          texto : "",
          resp1 : "",
          resp1C: false,
          resp2 : "",
          resp2C: false,
          resp3 : "",
          resp3C: false,
          resp4 : "",
          resp4C: false,
          resp5 : "",
          resp5C: false,
          resp6 : "",
          resp6C: false,
          resp7 : "",
          resp7C: false,
          resp8 : "",
          resp8C: false,
          tipo: "multiChoice",
          archivo:""
      }
  }
  
  handleChange = name => event => {
    this.setState({ [name]: event.target.checked });
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
  componentDidMount = () => {
      fetch("edicionPregunta.json", {
          method: "GET"
      }).then(response => response.json())
        .then( json =>{
            this.setState ({
            idpregunta : json.idPregunta,
            texto : json.textoPregunta,
            resp1:json.textoRespuesta[0],
            resp1C:json.valorBooleano[0],
            resp2:json.textoRespuesta[1],
            resp2C:json.valorBooleano[1],
            resp3:json.textoRespuesta[2],
            resp3C:json.valorBooleano[2],
            resp4:json.textoRespuesta[3],
            resp4C:json.valorBooleano[3],
            resp5:json.textoRespuesta[4],
            resp5C:json.valorBooleano[4],
            resp6:json.textoRespuesta[5],
            resp6C:json.valorBooleano[5],
            resp7:json.textoRespuesta[6],
            resp7C:json.valorBooleano[6],
            resp8:json.textoRespuesta[7],
            resp8C:json.valorBooleano[7],
      })})
  }
  render() {
    const { classes } = this.props;
    return (
      <main className={classes.main}>
        <CssBaseline />
      <Paper className={classes.paper}>
        <Avatar className={classes.avatar}>
            <Icon>check_box</Icon>
        </Avatar>
        
        <Typography component="h1" variant="h5">
          Pregunta Multichoice
        </Typography>
        <form action = "CrearPreguntaAction2" method = "post" className={classes.form} onSubmit={this.handleSubmit}>
          <input type='hidden' id='tipo' name='tipo' value='multiChoice' />
          <FormControl margin="normal" required fullWidth>
            <InputLabel>Id de Pregunta</InputLabel>
            <Input id="id" name="idpregunta" autoFocus value = {this.state.idpregunta} onChange = {this.handleInputChange}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel >Texto de la Pregunta</InputLabel>
            <Input id="texto" name="texto" value = {this.state.texto} onChange = {this.handleInputChange}/>
          </FormControl>
         <br/>
         <br/>s
        <Grid container spacing={4}>
            <Grid container item xs={16} spacing={24} justify="center">
                <Grid item xs={8}>
                    <Typography variant="h6" gutterBottom>
                        Respuestas
                    </Typography>
                </Grid>
                <Grid item xs={4}>
                    <Typography variant="h6" gutterBottom>
                        Correcta
                    </Typography>
                </Grid>
            </Grid>
            
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 1</InputLabel>
                        <Input id="resp1" name="resp1" value = {this.state.resp1} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp1C}
                            onChange={this.handleChange('resp1C')}
                            value={this.state.resp1C}
                            inputProps={{name: "resp1C"}}
                        />
                        }
                    label={this.state.resp1C.toString()}
                    />
                </Grid>
            </Grid>
            
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 2</InputLabel>
                        <Input id="resp2" name="resp2" value = {this.state.resp2} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp2C}
                            onChange={this.handleChange('resp2C')}
                            value={this.state.resp2C}
                            inputProps={{name: "resp2C"}}
                        />
                        }
                    label={this.state.resp2C.toString()}
                    />
                </Grid>
            </Grid>
            
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 3</InputLabel>
                        <Input id="resp3" name="resp3" value = {this.state.resp3} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp3C}
                            onChange={this.handleChange('resp3C')}
                            value={this.state.resp3C}
                            inputProps={{name: "resp3C"}}
                        />
                        }
                    label={this.state.resp3C.toString()}
                    />
                </Grid>
            </Grid>
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 4</InputLabel>
                        <Input id="resp4" name="resp4" value = {this.state.resp4} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp4C}
                            onChange={this.handleChange('resp4C')}
                            value={this.state.resp4C}
                            inputProps={{name: "resp4C"}}
                        />
                        }
                    label={this.state.resp4C.toString()}
                    />
                </Grid>
            </Grid>
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 5</InputLabel>
                        <Input id="resp5" name="resp5" value = {this.state.resp5} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp5C}
                            onChange={this.handleChange('resp5C')}
                            value={this.state.resp5C}
                            inputProps={{name: "resp5C"}}
                        />
                        }
                    label={this.state.resp5C.toString()}
                    />
                </Grid>
            </Grid>
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 6</InputLabel>
                        <Input id="resp6" name="resp6" value = {this.state.resp6} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp6C}
                            onChange={this.handleChange('resp6C')}
                            value={this.state.resp6C}
                            inputProps={{name: "resp6C"}}
                        />
                        }
                    label={this.state.resp6C.toString()}
                    />
                </Grid>
            </Grid>
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 7</InputLabel>
                        <Input id="resp7" name="resp7" value = {this.state.resp7} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4}>
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp7C}
                            onChange={this.handleChange('resp7C')}
                            value={this.state.resp7C}
                            inputProps={{name: "resp7C"}}
                        />
                        }
                    label={this.state.resp7C.toString()}
                    />
                </Grid>
            </Grid>
            <Grid container item xs={16} spacing={24} justify="center" alignItems="flex-end">
                <Grid item xs={8}>
                    <FormControl margin="normal"  fullWidth>
                        <InputLabel >Respuesta 8</InputLabel>
                        <Input id="resp8" name="resp8" value = {this.state.resp8} onChange = {this.handleInputChange}/>
                    </FormControl>
                </Grid>
                <Grid item xs={4} >
                    <FormControlLabel
                        control={
                        <Switch
                            checked={this.state.resp8C}
                            onChange={this.handleChange('resp8C')}
                            value={this.state.resp8C}
                            inputProps={{name: "resp8C"}}
                        />
                        }
                    label={this.state.resp8C.toString()}
                    />
                </Grid>
            </Grid>
        </Grid>
         <br/>
         <br/>
         <Input type = "file" name = "link" size = "45"/>
         <br/>
         <br/>
         <Button
          
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={event => this.handleSubmit(event)}
         >
            Guardar
         </Button>
        
        
        <a href="TablaPreguntasAction2">
            <Button fullWidth variant="outlined" color="secondary">Regresar</Button>
        </a>
        </form>
      </Paper>
      </main>
    );
  }
}
const EditarPregunta = withStyles(styles)(Index);
ReactDOM.render(<EditarPregunta />, document.getElementById('root'));